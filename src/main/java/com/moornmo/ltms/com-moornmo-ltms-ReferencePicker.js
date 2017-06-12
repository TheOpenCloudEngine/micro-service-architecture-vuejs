var template;
{
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "com-moornmo-ltms-ReferencePicker.html", false);
    xhr.onload = function () {
        template = xhr.responseText
    }
    xhr.send();
}


Vue.component('com-moornmo-ltms-referencepicker', {
    template: template,
    props: {
        data: Object,
        options: Object,
    },

    data: function () {

        var xhr = new XMLHttpRequest();
        var columns = [];
        var self = this;
        xhr.open('GET', "http://localhost:8080/classdefinition?className=" + this.options.class, false);
        xhr.onload = function () {
            var metadata = JSON.parse(xhr.responseText)
            columns = metadata.fieldDescriptors;

            for(var i=0; i<columns.length; i++){
                var item = columns[i];

                if(item.attributes && item.attributes['hidden']){
                    columns.splice(i, 1);
                    i--;
                }else if(item.className == "long" || item.className == "java.lang.Long" || item.className == "java.lang.Integer"){
                    item.type = "number";
                }else if(item.className == "java.util.Date" || item.className == "java.util.Calendar"){
                    item.type = "date";
                }else if(item.className.indexOf('[L') == 0 && item.className.indexOf(";") > 1){
                    item.component = "object-grid"
                    item.elemClassName = item.className.substring(2, item.className.length - 1);

                }else if(item.collectionClass){
                    item.component = "object-grid"
                    item.elemClassName = item.collectionClass;

                }
            }

            if(self.columnChanger){
                self.columnChanger(columns);
            }
        }
        xhr.send();


        return {
            rowData: [],
            columns: columns,
            java: this.options.class
        };
    },

    created: function(){


        if(this.online){

            var pathElements = this.java.split(".");
            var path = pathElements[pathElements.length-1].toLowerCase();
            var xhr = new XMLHttpRequest()
            var self = this

            xhr.open('GET', "http://localhost:8080/" + path, false);

            xhr.onload = function () {
                var jsonData = JSON.parse(xhr.responseText)
                self.rowData = jsonData._embedded[path];
            }
            xhr.send();

        }

    },
    computed: {
        filteredData: function () {
            var data = this.rowData

            return data
        }
    },
    filters: {
        capitalize: function (str) {
            return str.charAt(0).toUpperCase() + str.slice(1)
        }
    },
    methods: {
        sortBy: function (key) {
            this.sortKey = key
            this.sortOrders[key] = this.sortOrders[key] * -1
        },

        addRow: function(aRow){
            this.rowData.push(aRow);
        },

        showValue: function(key, entry){
            if(key.computed){
                return key.computed(entry);
            }else
                return entry[key.name];
        },

        onEvent: function(event, data){
            if(event == "saved"){
                this.addRow(data);
            }
        },
        addObject: function(aRow){
            if(!this.rowData) this.rowData = [];

            this.rowData.push(aRow);

            this.data = this.rowData;

        }
    }
})
var template;
{
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "object-grid.html", false);
    xhr.onload = function () {
        template = xhr.responseText
    }
    xhr.send();
}


Vue.component('object-grid', {
    template: template,
    props: {
        data: Array,
        columns: Array,
        filterKey: String,
        java: String,
        columnChanger: Object

    },

    created: function () {
        var xhr = new XMLHttpRequest()
        var self = this
        xhr.open('GET', "http://localhost:8080/metadata?className=" + this.java, false);
        xhr.onload = function () {
            var metadata = JSON.parse(xhr.responseText)
            self.columns = metadata.fieldDescriptors;

            if(self.columnChanger){
                self.columnChanger(self.columns);
            }
        }
        xhr.send();

        this.loadData();
    },

    filters: {
        capitalize: function (str) {
            return str.charAt(0).toUpperCase() + str.slice(1)
        },
    },

    methods: {
        loadData: function(){
            ///loads the data firstly

            var pathElements = this.java.split(".");
            var path = pathElements[pathElements.length-1].toLowerCase();
            var xhr = new XMLHttpRequest()
            var self = this

            xhr.open('GET', "http://localhost:8080/" + path, false);

            xhr.onload = function () {
                var jsonData = JSON.parse(xhr.responseText)
                self.data = jsonData._embedded[path];
            }
            xhr.send();
        },
        sortBy: function (key) {
            this.sortKey = key
            this.sortOrders[key] = this.sortOrders[key] * -1
        },

        showValue: function(key, entry){
            if(key.computed){
                return key.computed(entry);
            }else
                return entry[key.name];
        },

        addRow: function(aRow){
            // this.rows.push(aRow);
            this.$forceUpdate();
            this.loadData();
        },
        removeRow: function(row){
            //console.log(row);
            this.rows.$remove(row);
        },
        onEvent: function(event, data){
            if(event == "saved"){
                this.addRow(data);
            }
        }
    }
})
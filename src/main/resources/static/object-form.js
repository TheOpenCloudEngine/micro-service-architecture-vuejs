var template;
{
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "object-form.html", false);
    xhr.onload = function () {
        template = xhr.responseText
    }
    xhr.send();
}


Vue.component('object-form', {
    template: template,
    props: {
        columns: Array,
        java: String,
        data: Object,
        eventListeners: Array,
        online: Boolean
    },

    created: function () {
        var xhr = new XMLHttpRequest()
        var self = this
        xhr.open('GET', "http://localhost:8080/metadata?className=" + this.java, false);
        xhr.onload = function () {
            var metadata = JSON.parse(xhr.responseText)
            self.columns = metadata.fieldDescriptors;

            for(var i=0; i<self.columns.length; i++){
                var item = self.columns[i];

                if(item.attributes && item.attributes['hidden']){
                    self.columns.splice(i, 1);
                    i--;
                }else if(item.className == "long" || item.className == "java.lang.Long" || item.className == "java.lang.Integer"){
                    item.type = "number";
                }else if(item.className == "java.util.Date" || item.className == "java.util.Calendar"){
                    item.type = "date";
                }else if(item.className.indexOf('[L') == 0 && item.className.indexOf(";") > 1){
                    item.component = "object-grid"
                    item.elemClassName = item.className.substring(2, item.className.length - 1);

                }
            }

        }
        xhr.send();

    },

    methods:{
        submit_: function(){

            var pathElements = this.java.split(".");
            var path = pathElements[pathElements.length-1].toLowerCase();

            console.log(this.data);

            var xhr = new XMLHttpRequest()
            var self = this
            xhr.open('POST', "http://localhost:8080/" + path, false);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onload = function () {
                console.log(xhr);

            }
            xhr.send(JSON.stringify(this.data));

            if(this.eventListeners){
                this.eventListeners.forEach(function(listenerRef){
                    var listener = self.$root.$refs[listenerRef];

                    if(listener.onEvent){
                        listener.onEvent('saved', self.data);
                    }
                });
            }
        }

    }

})


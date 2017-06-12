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
        online: Boolean,
        options: Object
    },

    created: function () {
        var xhr = new XMLHttpRequest()
        var self = this

        xhr.open('GET', "http://localhost:8080/classdefinition?className=" + this.java, false);
        xhr.setRequestHeader("access_token", localStorage['access_token']);
        xhr.onload = function () {
            var metadata = JSON.parse(xhr.responseText)
            self.columns = metadata.fieldDescriptors;
            self.options = {};

            for (var i = 0; i < self.columns.length; i++) {
                var fd = self.columns[i];

                if (fd.options && fd.values) {
                    fd.optionMap = {};
                    for (var keyIdx in fd.options) {
                        var key = fd.options[keyIdx];
                        fd.optionMap[key] = fd.values[keyIdx];
                    }

                    self.options[fd.name] = fd.optionMap;
                } else {
                    self.options[fd.name] = {};
                }


                if (fd.attributes && fd.attributes['hidden']) {
                    self.columns.splice(i, 1);
                    i--;
                } else if (fd.optionMap && fd.optionMap['vue-component'] && Vue.options.components[fd.optionMap['vue-component']]) {
                    fd.component = fd.optionMap['vue-component'];
                } else if (fd.className == "long" || fd.className == "java.lang.Long" || fd.className == "java.lang.Integer") {
                    fd.type = "number";
                } else if (fd.className == "java.util.Date" || fd.className == "java.util.Calendar") {
                    fd.type = "date";
                } else if (fd.className.indexOf('[L') == 0 && fd.className.indexOf(";") > 1) {
                    fd.component = "object-grid"
                    fd.elemClassName = fd.className.substring(2, fd.className.length - 1);

                    //self.options[fd.name]['editable'] = true;

                } else if (fd.collectionClass) {
                    fd.component = "object-grid"
                    fd.elemClassName = fd.collectionClass;

                    //self.options[fd.name]['editable'] = true;

                }
            }

        }
        xhr.send();

    },

    methods: {
        submit_: function () {

            var pathElements = this.java.split(".");
            var path = pathElements[pathElements.length - 1].toLowerCase();

            console.log(this.data);

            var xhr = new XMLHttpRequest()
            var self = this
            xhr.open('POST', "http://localhost:8080/" + path, false);
            //xhr.setRequestHeader("Content-Type", "application/json");
            xhr.setRequestHeader("access_token", localStorage['access_token']);
            xhr.overrideMimeType("application/json; charset=UTF-8");
            xhr.onload = function () {
                console.log(xhr);

            }
            xhr.send(JSON.stringify(this.data));

            if (this.eventListeners) {
                this.eventListeners.forEach(function (listenerRef) {
                    var listener = self.$root.$refs[listenerRef];

                    if (listener.onEvent) {
                        listener.onEvent('saved', self.data);
                    }
                });
            }
        }

    }

})


(function ($) {
    var KEditor = $.keditor;
    var flog = KEditor.log;

    KEditor.components['object-form'] = {
        init: function (contentArea, container, component, keditor) {
            flog('init "photo" component', component);

            var componentContent = component.children('.keditor-component-content');
            var img = componentContent.find('img');

            img.css('display', 'inline-block');
        },

        settingEnabled: true,

        settingTitle: 'Object Form Settings',

        initSettingForm: function (form, keditor) {
            flog('initSettingForm "photo" component');

            var self = this;
            var options = keditor.options;

            form.append(
                '<form class="form-horizontal">' +
                '   <div class="form-group">' +
                '       <label for="photo-width" class="col-sm-12">Java</label>' +
                '       <div class="col-sm-12">' +
                '           <input id="java" class="form-control" />' +
                '       </div>' +
                '   </div>' +
                '</form>'
            );

            var java = form.find('#java');
            java.on('change', function () {
                var id = keditor.getSettingComponent()[0].id;
                keditor.vueInstances[id].$children[0].java = java.val();
            });
        },

        showSettingForm: function (form, component, keditor) {
            flog('showSettingForm "photo" component', component);

            var self = this;

        }
    };

})(jQuery);

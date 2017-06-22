var template;
{
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "class-selector.html", false);
    xhr.onload = function () {
        template = xhr.responseText
    }
    xhr.send();
}


Vue.component('class-selector', {
    template: template,
    props: {
        data: String,
    },

    data:function(){

        return {
            classTypes: [
                {
                    displayName: '문자열',
                    className: 'java.lang.String'
                },
                {
                    displayName: '정수형',
                    className: 'java.lang.int'
                },
                {
                    displayName: '실수형',
                    className: 'java.lang.double'
                },
                {
                    displayName: '날짜',
                    className: 'java.util.Date'
                },
            ]
        };
    },

})


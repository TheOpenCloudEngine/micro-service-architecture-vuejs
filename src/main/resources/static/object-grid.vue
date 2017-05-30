<template>

    <md-table v-once>
        <md-table-header>
            <md-table-row>
                <md-table-head v-for="key in columns"
                               md-sort-by="key">
                {{ key.displayName | capitalize }}
                </md-table-head>
            </md-table-row>
        </md-table-header>
        <md-table-body>
        <md-table-row v-for="entry in filteredData">
            <md-table-cell v-for="key in columns">
                {{entry[key.name]}}
            </md-table-cell>
        </md-table-row>
        </md-table-body>
    </md-table>
</template>

<script>


    module.exports = {
        props: {
            data: Array,
            columns: Array,
            filterKey: String,
            java: String,

        },

        created: function () {
            var xhr = new XMLHttpRequest()
            var self = this
            xhr.open('GET', "http://localhost:8080/metadata?className=" + this.java, false);
            xhr.onload = function () {
                var metadata = JSON.parse(xhr.responseText)
                self.columns = metadata.fieldDescriptors;
            }
            xhr.send();

        },

        data: function () {


            var sortOrders = {}

            if(this.columns) {
                this.columns.forEach(function (key) {
                    sortOrders[key] = 1
                });
            }

            return {
                sortKey: '',
                sortOrders: sortOrders
            }
        },
        computed: {
            filteredData: function () {
                var sortKey = this.sortKey
                var filterKey = this.filterKey && this.filterKey.toLowerCase()
                var order = this.sortOrders[sortKey] || 1
                var data = this.data
                if (filterKey) {
                    data = data.filter(function (row) {
                        return Object.keys(row).some(function (key) {
                            return String(row[key]).toLowerCase().indexOf(filterKey) > -1
                        })
                    })
                }
                if (sortKey) {
                    data = data.slice().sort(function (a, b) {
                        a = a[sortKey]
                        b = b[sortKey]
                        return (a === b ? 0 : a > b ? 1 : -1) * order
                    })
                }
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
            }
        }
    })



</script>

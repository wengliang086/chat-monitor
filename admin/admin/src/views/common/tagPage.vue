<template>
<div>
    <el-tabs v-model="activeTabIndex" type="card" editable v-on:edit="handleTabsEdit" @tab-click="tabClick">
        <el-tab-pane v-for="(item, index) in tabsList" :key="index" v-bind:name="item.route" :label="item.name" closable>
        </el-tab-pane>
    </el-tabs>
</div>
</template>

<script>
export default {
    computed: {
        tabsList() {
            return this.$store.state.app.tabsList;
        },
        activeTabIndex: {
            get() {
                return this.$store.state.app.activeTabIndex;
            },
            set(val) {
                // alert("set " + val)
                this.$store.commit('setActiveTab', val);
            }
        }
    },
    methods: {
        // tab切换时，动态的切换路由
        tabClick(tab) {
            let path = this.activeTabIndex;
            // alert("tabClick path=" + path)
            this.$router.push({
                path: path
            });
        },
        handleTabsEdit(targetName, action) {
            // alert(targetName + " " + action);
            if (action === "add") {
                let newTabName = ++this.tabIndex + '';
                this.tagPageList.push({
                    name: newTabName,
                    content: 'newTab'
                });
                this.editableTabsValue = newTabName;
            }
            if (action === 'remove') {
                let tabs = this.tagPageList;
                let activeName = this.editableTabsValue;
                if (activeName === targetName) {
                    alert(activeName + " - " + targetName);
                    tabs.forEach((tab, index) => {
                        alert(tab.name + " - " + targetName);
                        if (tab.name === targetName) {
                            let nextTab = tabs[index + 1] || tabs[index - 1];
                            if (nextTab) {
                                activeName = nextTab.name;
                            }
                        }
                    });
                }
                this.editableTabsValue = activeName;
                this.tagPageList = tabs.filter(tab => tab.name !== targetName);
            }
        }
    },
    watch: {
        '$route'(to) {
            let toPath = "/" + to.path.split('/')[1];
            // console.log(toPath, to);
            let flag = false;
            for (const tab of this.tabsList) {
                if (tab.name === to.name) {
                    flag = true;
                    this.$store.commit('setActiveTab', toPath);
                    break
                }
            }
            if (!flag) {
                this.$store.commit('addTab', {
                    route: toPath,
                    name: to.name
                });
                this.$store.commit('setActiveTab', toPath);
            }
        }
    }
};
</script>

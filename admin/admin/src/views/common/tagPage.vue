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
            if (action === 'remove') {
                this.handleTabsDel(targetName);
            }
        },
        handleTabsDel(targetName) {
            // alert("del " + targetName);
            // 首页不可删除
            if (targetName == '/game') {
                return;
            }
            if (this.activeTabIndex === targetName) {
                // 设置当前激活的路由
                this.tabsList.forEach((tab, index) => {
                    if (tab.route === targetName) {
                        let nextTab = this.tabsList[index + 1] || this.tabsList[index - 1];
                        if (nextTab) {
                            this.$store.commit('setActiveTab', nextTab.route);
                            this.$router.push({
                                path: nextTab.route
                            });
                        }
                    }
                });
            }
            this.$store.commit('deleteTab', targetName);
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
    },
    mounted() {
        // 加入首页
        const firstRoute = '/game';
        this.$store.commit('addTab', {
            route: firstRoute,
            name: "游戏列表"
        });
        if (this.$route.path === firstRoute) {
            this.$store.commit('setActiveTab', firstRoute);
        } else {
            this.$store.commit('addTab', {
                route: this.$route.path,
                name: this.$route.name
            });
            this.$store.commit('setActiveTab', this.$route.path);
        }
    }
};
</script>

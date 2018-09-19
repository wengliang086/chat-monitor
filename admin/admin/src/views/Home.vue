<template>
<el-row class="container">
    <imp-header v-on:collapseChanged="updateCollapse($event)" v-bind:subSysName="sysName"></imp-header>
    <el-col :span="24" class="main">
        <imp-side-menu v-bind:collapsed="collapsed"></imp-side-menu>
        <section class="content-container">
            <div class="grid-content bg-purple-light">
                <el-col :span="24" class="breadcrumb-container">
                    <strong class="title">{{$route.name}}</strong>
                    <el-breadcrumb separator="/" class="breadcrumb-inner">
                        <el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
                            {{ item.name }}
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
                <el-col :span="24" class="content-wrapper">
                    <transition name="fade" mode="out-in">
                        <router-view></router-view>
                    </transition>
                </el-col>
            </div>
        </section>
    </el-col>
</el-row>
</template>

<script>
import impHeader from "./common/header";
import impSideMenu from "./common/sideMenu";

export default {
    components: {
        impHeader,
        impSideMenu
    },
    data() {
        return {
            sysName: "聊天监控管理台",
            collapsed: false
        };
    },
    methods: {
        updateCollapse(collapse) {
            this.collapsed = collapse;
        }
    },
    mounted() {
        var user = sessionStorage.getItem("user");
        if (user) {
            user = JSON.parse(user);
            this.sysUserName = user.account || "";
            this.sysUserAvatar =
                user.avatar ||
                "https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png";
        }
    }
};
</script>

<style lang="scss" scoped>
@import "~scss_vars";

.container {
    position: absolute;
    top: 0px;
    bottom: 0px;
    width: 100%;

    .main {
        display: flex;
        // background: #324057;
        position: absolute;
        top: 60px;
        bottom: 0px;
        overflow: hidden;

        .content-container {
            // background: #f1f2f7;
            flex: 1;
            // position: absolute;
            // right: 0px;
            // top: 0px;
            // bottom: 0px;
            // left: 230px;
            overflow-y: scroll;
            padding: 20px;

            .breadcrumb-container {

                //margin-bottom: 15px;
                .title {
                    width: 200px;
                    float: left;
                    color: #475669;
                }

                .breadcrumb-inner {
                    float: right;
                }
            }

            .content-wrapper {
                background-color: #fff;
                box-sizing: border-box;
            }
        }
    }
}
</style>

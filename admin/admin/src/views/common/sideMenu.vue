<template>
<!-- <el-scrollbar style="overflow-x: hidden;"> -->
<aside :class="collapsed?'menu-collapsed':'menu-expanded'">
    <!--导航菜单-->
    <el-menu :default-active="$route.path" background-color="#eef0f6" router :collapse="collapsed">
        <!-- 一级菜单展开 -->
        <template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
            <el-submenu :index="index+''" v-if="!item.leaf" :key="index+''">
                <template slot="title">
                    <i :class="item.iconCls"></i>
                    <span slot="title">{{item.name}}</span>
                </template>
                <!-- 二级菜单展开 -->
                <template v-for="(child,index1) in item.children" v-if="!child.hidden">
                    <el-submenu :index="index+''+index1" v-if="child.children&&child.children.length>0" :key="index+''+index1">
                        <template slot="title">
                            {{child.name}}
                        </template>
                        <!-- 三级菜单展开 -->
                        <template v-for="(child2,index2) in child.children" v-if="!child2.hidden">
                            <el-submenu :index="index+''+index1+''+index2" v-if="child2.children&&child2.children.length>0" :key="index+''+index1+''+index2">
                                <template slot="title">
                                    {{child2.name}}
                                </template>
                                <!-- 四级菜单展开 -->
                                <el-menu-item v-for="child3 in child2.children" :index="child3.path" :key="child3.path" v-if="!child3.hidden">
                                    {{child3.name}}
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item :index="child2.path" :key="child2.path" v-else>
                                {{child2.name}}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                    <el-menu-item class="el-menu-vertical-demo2" :index="child.path" :key="child.path" v-else>
                        {{child.name}}
                    </el-menu-item>
                </template>
            </el-submenu>
            <el-menu-item v-if="item.leaf&&item.children.length>0" :index="item.children[0].path" :key="item.children[0].path">
                <i :class="item.iconCls"></i>
                <span slot="title">{{item.children[0].name}}</span>
            </el-menu-item>
        </template>
    </el-menu>
</aside>
<!-- </el-scrollbar> -->
</template>

<script>
export default {
    props: ["collapsed"],
    methods: {
        showMenu(i, status) {
            this.$refs.menuCollapsed.getElementsByClassName(
                "submenu-hook-" + i
            )[0].style.display = status ? "block" : "none";
        }
    }
};
</script>

<style lang="scss" scoped>
@import "~scss_vars";

aside {
    flex: 0 0 230px;
    width: 230px;
    height: 100%;
    overflow-x: hidden;
    overflow-y: auto;
    background-color: rgb(238, 240, 246);

    // position: absolute;
    // top: 0px;
    // bottom: 0px;
    .el-menu {
        height: auto;
    }

    .collapsed {
        width: 60px;

        .item {
            position: relative;
        }

        .submenu {
            position: absolute;
            top: 0px;
            left: 60px;
            z-index: 99999;
            height: auto;
            display: none;
        }

        .submenu2 {
            position: absolute;
            top: 0px;
            left: 200px;
            z-index: 99999;
            height: auto;
            display: none;
        }
    }
}

.menu-collapsed {
    flex: 0 0 60px;
    width: 60px;
}

.menu-expanded {
    flex: 0 0 230px;
    width: 230px;
}

// 在common.css中添加
.el-scrollbar__wrap {
    overflow-x: hidden;
}

.el-scrollbar__view {
    height: 100%;
    overflow-x: hidden;
}
</style>

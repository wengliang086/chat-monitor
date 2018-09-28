<template>
<!-- <el-scrollbar style="overflow-x: hidden;"> -->
<aside :class="collapsed?'menu-collapsed':'menu-expanded'">
    <!--导航菜单-->
    <el-menu :default-active="$route.path" class="el-menu-vertical-demo" @open="handleopen" @close="handleclose" @select="handleselect" router v-show="!collapsed" :style="collapsed ? 'width: 60px; overflow: hidden;' : 'width: 230px; overflow: hidden;'">
        <!-- 一级菜单展开 -->
        <template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
            <el-submenu :index="index+''" v-if="!item.leaf" :key="index+''">
                <template slot="title">
                    <i :class="item.iconCls"></i>{{item.name}}
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
                <i :class="item.iconCls"></i>{{item.children[0].name}}
            </el-menu-item>
        </template>
    </el-menu>
    <!--导航菜单-折叠后-->
    <ul class="el-menu el-menu-vertical-demo collapsed" v-show="collapsed" ref="menuCollapsed">
        <!-- 一级菜单展开 -->
        <li v-for="(item,index) in $router.options.routes" :key="index" v-if="!item.hidden" class="el-submenu item">
            <template v-if="!item.leaf">
                <div class="el-submenu__title" style="padding-left: 20px;" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)"><i :class="item.iconCls"></i></div>
                <ul class="el-menu submenu" :class="'submenu-hook-'+index" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)">
                    <!-- 二级菜单展开 -->
                    <li v-for="(child,index2) in item.children" :key="index2">
                        <ul v-if="child.children" style="-webkit-padding-start: 0px;">
                            <div class="el-submenu__title" style="padding-left: 40px;" @mouseover="showMenu(index+''+index2,true)" @mouseout="showMenu(index+''+index2,false)">{{child.name}}-></div>
                            <ul class="el-menu submenu2" :class="'submenu-hook-'+index+''+index2" @mouseover="showMenu(index+''+index2,true)" @mouseout="showMenu(index+''+index2,false)">
                                <!-- 三级菜单展开 -->
                                <li v-for="(child2,index3) in child.children" :key="index3">
                                    <ul v-if="child2.children" style="-webkit-padding-start: 0px;">
                                        <div class="el-submenu__title" style="padding-left: 40px;" @mouseover="showMenu(index+''+index2+''+index3,true)" @mouseout="showMenu(index+''+index2+''+index3,false)">{{child2.name}}-></div>
                                        <ul class="el-menu submenu2" :class="'submenu-hook-'+index+''+index2+''+index3" @mouseover="showMenu(index+''+index2+''+index3,true)" @mouseout="showMenu(index+''+index2+''+index3,false)">
                                            <!-- 四级菜单展开 -->
                                            <li v-for="(child3,index4) in child2.children" :key="index4">
                                                <ul class="el-menu-item" style="padding-left: 40px;height: 56px;" :class="$route.path==child3.path?'is-active':''" @click="$router.push(child3.path)">{{child3.name}}</ul>
                                            </li>
                                        </ul>
                                    </ul>
                                    <ul v-else class="el-menu-item" style="padding-left: 40px;height: 56px;" :class="$route.path==child2.path?'is-active':''" @click="$router.push(child2.path)">{{child2.name}}</ul>
                                </li>
                            </ul>
                        </ul>
                        <ul v-else class="el-menu-item" style="padding-left: 40px;height: 56px;" :class="$route.path==child.path?'is-active':''" @click="$router.push(child.path)">{{child.name}}</ul>
                    </li>
                </ul>
                <!-- <div class="el-submenu__title" style="padding-left: 20px;" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)"><i :class="item.iconCls"></i></div>		<ul class="el-menu submenu" :class="'submenu-hook-'+index" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)">		<li v-for="child in item.children" v-if="!child.hidden" :key="child.path" class="el-menu-item" style="padding-left: 40px;" :class="$route.path==child.path?'is-active':''" @click="$router.push(child.path)">{{child.name}}</li>		</ul> -->
            </template>
            <template v-else>
        <li class="el-submenu">
            <div class="el-submenu__title el-menu-item" style="padding-left: 20px;height: 56px;line-height: 56px;padding: 0 20px;" :class="$route.path==item.children[0].path?'is-active':''" @click="$router.push(item.children[0].path)"><i :class="item.iconCls"></i></div>
        </li>
        </template>
        </li>
    </ul>
</aside>
<!-- </el-scrollbar> -->
</template>

<script>
export default {
    props: ["collapsed"],
    methods: {
        handleopen() {
            // console.log("handleopen");
        },
        handleclose() {
            // console.log("handleclose");
        },
        handleselect: function (a, b) {},
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

    .el-menu-vertical-demo {
        background-color: rgb(238, 240, 246);
    }

    .el-menu-vertical-demo2 {
        background-color: rgb(228, 231, 240);
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

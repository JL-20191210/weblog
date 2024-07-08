<template>
    <!-- 外层容器 -->
    <el-container>

        <!-- 左边侧边栏 -->
        <el-aside :width='menuStore.menuWidth'>
            <AdminMenu></AdminMenu>
            左边侧边栏
        </el-aside>

        <el-container>
            <el-header>
                <AdminHeader></AdminHeader>
            </el-header>

            <!-- 主容器 -->
            <el-main>
                <!-- 标签导航栏 -->
                <AdminTagList></AdminTagList>
                <!-- 主内容（根据路由动态展示不同页面） -->
                <router-view></router-view>
            </el-main>

            <!-- 底栏容器 -->
            <el-footer>
                <AdminFooter></AdminFooter>
            </el-footer>
        </el-container>
    </el-container>
</template>

<script setup>
//引入组件 
import AdminMenu from './components/AdminMenu.vue'
import AdminHeader from './components/AdminHeader.vue'
import AdminTagList from './components/AdminTagList.vue'
import AdminFooter from './components/AdminFooter.vue'
import { useMenuStore } from '@/stores/menu'

const menuStore = useMenuStore()
</script>

<style scoped>
.el-header {
    padding: 0 !important;
}
</style>
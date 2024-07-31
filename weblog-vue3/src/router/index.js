import Index from "@/pages/frontend/index.vue";
import Login from '@/pages/admin/login.vue';
import AdminIndex from '@/pages/admin/index.vue'
import Admin from '@/layouts/admin/admin.vue';
import AdminArticleList from'@/pages/admin/article-list.vue'
import AdminCategoryList from '@/pages/admin/category-list.vue'
import AdminTagList from '@/pages/admin/tag-list.vue'
import AdminBlogSetting from '@/pages/admin/blog-setting.vue'
import ArchiveList from '@/pages/frontend/archive-list.vue'
import TagArticleList from '@/pages/frontend/tag-article-list.vue'
import ArticleDetail from '@/pages/frontend/article-detail.vue'

import {createRouter,createWebHashHistory} from 'vue-router'
import CategoryList from "@/pages/frontend/category-list.vue";
import CategoryArticleList from "@/pages/frontend/category-article-list.vue";
import TagList from "@/pages/frontend/tag-list.vue";

//统一在这里声明所有路由
const routes = [
    {
        path: '/',//路由地址
        component: Index,//对应组件
        meta: {//meta信息
            title: 'weblog首页'//页面标题
        }
    },
    {
        path: '/login',//路由地址
        component: Login,//对应组件
        meta: {//meta信息
            title: 'weblog 登陆页'//页面标题
        }
    },
    {
        path: '/archive/list', // 归档页
        component: ArchiveList,
        meta: { // meta 信息
            title: 'Weblog 归档页'
        }
    },
    {
        path: '/category/list',//分类页
        component: CategoryList,
        meta: {// meta信息
            title: 'Weblog分类页'
        }
    },
    {
        path: '/category/article/list',//分类页
        component: CategoryArticleList,
        meta: {// meta信息
            title: 'Weblog分类文章页'
        }
    },
    {
        path: '/tag/list',//标签列表页
        component: TagList,
        meta: {// meta信息
            title: 'Weblog标签列表页'
        }
    },
    {
        path: '/tag/article/list', // 标签列表页
        component: TagArticleList,
        meta: { // meta 信息
            title: 'Weblog 标签文章页'
        }
    },
    {
        path: '/article/:articleId', // 文章详情页
        component: ArticleDetail,
        meta: { // meta 信息
            title: 'Weblog 详情页'
        }
    },
    {
        path:"/admin",//后台首页
        component:Admin,//对应admin.vue的布局文件
        // 使用到admin.vue布局的，都要放置在其子路由下面
        children:[
            {
                path:"/admin/index",
                component:AdminIndex,
                meta:{
                    title:"Admin后台首页"
                }
            },
            {
                path:"/admin/article/list",
                component:AdminArticleList,
                meta:{
                    title:"文章管理"
                }
            },

            {
                path:"/admin/category/list",
                component:AdminCategoryList,
                meta:{
                    title:"分类管理"
                }
            },
            {
                path:"/admin/tag/list",
                component:AdminTagList,
                meta:{
                    title:"标签管理"
                }
            },
            {
                path:"/admin/blog/setting",
                component:AdminBlogSetting,
                meta:{
                    title:"博客设置"
                }
            },

        ]
    },
]

//创建路由
const router = createRouter({
    // 指定路由的历史管理方式，hash 模式指的是 URL 的路径是通过 hash 符号（#）进行标识
    history: createWebHashHistory(),

    // routes: routes 的缩写
    routes,
})

//ES6模块到处语句，它用于将router对象导出，以便其他文件可以导入和使用这个对象
export default router

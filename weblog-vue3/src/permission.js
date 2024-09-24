import router  from "@/router/index";
import { getToken } from "@/composables/cookie";
import { hidePageLoading, showMessage, showPageLoading } from "@/composables/util";
import { useBlogSettingsStore } from "./stores/blogsettings";

// 全局路由前置守卫
router.beforeEach((to,from,next)=>{
    console.log('==>全局路由前置守卫')

    // 展示页面加载 Loading
    showPageLoading()
    
    let token = getToken()
    if(!token&&to.path.startsWith('/admin')){
        showMessage('请先登录','warning')
        next({path:'/login'})
    }else if(token&&to.path=='/login'){
        showMessage('请勿重复登录','warning')
        next({path:'/admin/index'})
    }else if(!to.path.startsWith('/admin')){
        // 如果访问的非 /admin 前缀路由
        // 引入博客设置 store
        let blogsettingsStore = useBlogSettingsStore()
        // 获取博客设置信息并保存到全局状态中
        blogsettingsStore.getBlogSettings()
        next()
    }
    else{
        next()
    }
})

// 全局路由后置守卫
router.afterEach((to,form)=>{
    // 动态设置页面 Title
    let title = (to.meta.title?to.meta.title:'')+' -小满'
    document.title = title

    // 隐藏页面加载 Loading
    hidePageLoading()
})
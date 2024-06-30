import '@/assets/main.css'
import 'animate.css';
import 'nprogress/nprogress.css'

import { createApp } from 'vue'
import App from '@/App.vue'
// 导入路由
import router from '@/router'
// 导入全局路由守卫
import '@/permission'
// 导入 Element Plus 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import pinia from '@/stores'
const app = createApp(App)
// 应用Pinia
app.use(router)
app.mount('#app')
app.use(pinia)

// 引入图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

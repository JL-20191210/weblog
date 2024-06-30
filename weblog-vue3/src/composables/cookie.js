import { useCookies } from '@vueuse/integrations/useCookies'

// ============================== Token 令牌 ==============================
//存储在Cookie中的Token的Key
const TOKEN_KEY = 'Authorization'
const cookie = useCookies()

//获取Token值
export function getToken(){
    return cookie.get(TOKEN_KEY)
}

//设置Token到Cookie中
export function setToken(token){
    return cookie.set(TOKEN_KEY,token)
}

//删除Token
export function removeToken(){
    return cookie.remove(TOKEN_KEY)
}

// ============================== 标签页 ==============================
const TAB_LIST_KEY = 'tabList'

// 获取tablist
export function getTabList(){
    return cookie.get(TAB_LIST_KEY)
}

// 存储tablist
export function setTabList(tabList){
    return cookie.set(TAB_LIST_KEY,tabList)
}
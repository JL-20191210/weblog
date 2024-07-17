import axios from "@/axios"

// 获取分类分页参数
export function getCategoryPageList(data){
    return axios.post("/admin/category/list",data)
}

// 添加分类
export function addCategory(data){
    return axios.post("/admin/category/add",data)
}

// 删除分类
export function deleteCategory(id){
    // 包装成了一个对象的形式：{id : id} ， {id} 是其简写模式
    return axios.post("/admin/category/delete",{id})
}

export function getCategorySelectList(){
    return axios.post("/admin/category/select/list")
}
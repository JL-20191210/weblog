import axios from "@/axios"

// 获取标签分页参数
export function getTagPageList(data){
    return axios.post("/admin/tag/list",data)
}

// 添加标签
export function addTags(data){
    return axios.post("/admin/tag/add",data)
}

// 删除标签
export function deleteTag(id){
    // 包装成了一个对象的形式：{id : id} ， {id} 是其简写模式
    return axios.post("/admin/tag/delete",{id})
}
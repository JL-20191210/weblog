import axios from "@/axios"

// 获取标签分页参数
export function getTagPageList(data){
    return axios.post("/admin/tag/list",data)
}

// 添加标签
export function addTag(data){
    return axios.post("/admin/tag/add",data)
}

// 删除标签
export function deleteTag(id){
    // 包装成了一个对象的形式：{id : id} ， {id} 是其简写模式
    return axios.post("/admin/tag/delete",{id})
}

// 根据标签名模糊查询
export function searchTags(key){
    return axios.post("/admin/tag/search",{key})
}

//获取标签select列表数据
export function getTagSelectList(){
    return axios.post("/admin/tag/select/list")
}
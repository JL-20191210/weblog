import axios from "@/axios"

export function getBlogSettingDetail() {
    return axios.post("/admin/blog/settings/detail")
}

export function updateBlogSettings(data){
    return axios.post("/admin/blog/settings/update", data)
}
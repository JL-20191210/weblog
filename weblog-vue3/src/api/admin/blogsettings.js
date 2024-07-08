import axios from "@/axios"

export function getBlogSettingDetail() {
    return axios.post("/admin/blog/settings/detail")
}
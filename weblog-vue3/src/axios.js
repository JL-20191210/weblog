import axios from "axios";

//创建Axios实例
const instance = axios.create({
    baseURL: "http://localhost:8081/",//API基础URL
    timeout: 7000, //请求超时时间
})

//暴露出去
export default instance
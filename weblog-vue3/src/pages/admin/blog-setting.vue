<template>
    <div>
        <!-- 卡片组件， shadow="never" 指定 card 卡片组件没有阴影 -->
        <el-card shadow="never">
            <el-form ref="formRef" :model="form" label-width="160px" :rules="rules">
                <el-form-item label="博客名称" prop="name">
                    <el-input v-model="form.name" clearable></el-input>
                </el-form-item>
                <el-form-item label="作者名" prop="author">
                    <el-input v-model="form.author" clearable></el-input>
                </el-form-item>
                <el-form-item label="博客 LOGO" prop="logo">
                    <el-upload class="avatar-uploader"
                        action="#" :show-file-list="false"
                        :on-change="handleLogoChange" :auto-upload="false">
                        <img v-if="form.logo" :src="form.logo" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="作者头像" prop="avatar">
                    <el-upload class="avatar-uploader"
                        action="#" :show-file-list="false"
                        :on-change="handleAvatarChange" :auto-upload="false">
                        <img v-if="form.avatar" :src="form.avatar" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="介绍语" prop="introduction">
                    <el-input v-model="form.introduction" type="textarea"></el-input>
                </el-form-item>
                <el-form-item label="开启GitHub访问">
                    <el-switch v-model="isGithubChecked" inline-prompt :active-icon="Check" :inactive-icon="Close"
                        @change="githubSwitchChange" />
                </el-form-item>
                <el-form-item label="GitHub 主页访问地址" v-if="isGithubChecked">
                    <el-input v-model="form.githubHomepage" clearable placeholder="请输入GitHub主页访问的URL" />
                </el-form-item>
                <!-- 开启 Gitee 访问 -->
                <el-form-item label="开启 Gitee 访问">
                    <el-switch v-model="isGiteeChecked" inline-prompt :active-icon="Check" :inactive-icon="Close"
                        @change="giteeSwitchChange" />
                </el-form-item>
                <el-form-item label="Gitee 主页访问地址" v-if="isGiteeChecked">
                    <el-input v-model="form.giteeHomepage" clearable placeholder="请输入 Gitee 主页访问的 URL" />
                </el-form-item>

                <!-- 开启知乎访问 -->
                <el-form-item label="开启知乎访问">
                    <el-switch v-model="isZhihuChecked" inline-prompt :active-icon="Check" :inactive-icon="Close"
                        @change="zhihuSwitchChange" />
                </el-form-item>
                <el-form-item label="知乎主页访问地址" v-if="isZhihuChecked">
                    <el-input v-model="form.zhihuHomepage" clearable placeholder="请输入知乎主页访问的 URL" />
                </el-form-item>

                <!-- 开启 CSDN 访问 -->
                <el-form-item label="开启 CSDN 访问">
                    <el-switch v-model="isCSDNChecked" inline-prompt :active-icon="Check" :inactive-icon="Close"
                        @change="csdnSwitchChange" />
                </el-form-item>
                <el-form-item label="CSDN 主页访问地址" v-if="isCSDNChecked">
                    <el-input v-model="form.csdnHomepage" clearable placeholder="请输入 CSDN 主页访问的 URL" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit" :loading="btnLoading">保存</el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { Check, Close } from '@element-plus/icons-vue'
import { getBlogSettingDetail,updateBlogSettings } from '@/api/admin/blogsettings'
import { uploadFile } from '@/api/admin/file'
import { showMessage } from '@/composables/util'

// 表单对象
const form = reactive({
    name: '',
    author: '',
    logo: '',
    avatar: '',
    introduction: '',
    githubHomepage: '',
    giteeHomepage: '',
    zhihuHomepage: '',
    csdnHomepage: '',
})

//是否开启GitHub
const isGithubChecked = ref(false)
// 是否开启 Gitee
const isGiteeChecked = ref(false)
// 是否开启知乎
const isZhihuChecked = ref(false)
// 是否开启 CSDN
const isCSDNChecked = ref(false)

// 是否显示保存按钮的loading状态，默认为false
const btnLoading = ref(false)

// 表单引用
const formRef = ref(null)

// 监听 Github Switch 改变事件
const githubSwitchChange = (checked) => {
    if (checked == false) {
        form.githubHomepage = ''
    }
}

// 监听 Gitee Switch 改变事件
const giteeSwitchChange = (checked) => {
    if (checked == false) {
        form.giteeHomepage = ''
    }
}

// 监听知乎 Switch 改变事件
const zhihuSwitchChange = (checked) => {
    if (checked == false) {
        form.zhihuHomepage = ''
    }
}

// 监听 CSDN Switch 改变事件
const csdnSwitchChange = (checked) => {
    if (checked == false) {
        form.csdnHomepage = ''
    }
}

// 规则校验
const rules = {
    name: [{ required: true, message: '请输入博客名称', trigger: 'blur' }],
    author: [{ required: true, message: '请输入作者名', trigger: 'blur' }],
    logo: [{ required: true, message: '请上传博客 LOGO', trigger: 'blur' }],
    avatar: [{ required: true, message: '请上传作者头像', trigger: 'blur' }],
    introduction: [{ required: true, message: '请输入介绍语', trigger: 'blur' }],
}

//上传logo图片
const handleLogoChange = (file)=>{
    //表单对象
    let formData = new FormData()

    //添加file字段，并将文件传入
    formData.append('file',file.raw)

    uploadFile(formData).then((e)=>{
        // 响参数失败，提示错误信息
        if(e.success == false){
            let message = e.message
            showMessage(message,'error')
            return
        }

        //成功则设置logo链接，并提示成功
        form.logo = e.data.url
        showMessage('上传成功')
    })



}

//上传头像图片
const handleAvatarChange = (file)=>{
    //表单对象
    let formData = new FormData()

    //添加file字段，并将文件传入
    formData.append('file',file.raw)

    uploadFile(formData).then((e)=>{
        // 响参数失败，提示错误信息
        if(e.success == false){
            let message = e.message
            showMessage(message,'error')
            return
        }

        //成功则设置logo链接，并提示成功
        form.avatar = e.data.url
        showMessage('上传成功')
    })



}

const onSubmit = ()=>{
    // 先验证form表单字段
    formRef.value.validate((valid)=>{
        if(!valid){
            console.log('表单验证不通过');
            return false
        }

        // 显示保存按钮loading
        btnLoading.value = true
        updateBlogSettings(form).then((res)=>{
            if(res.success == false){
                let message = res.message
                showMessage(message,'error')
                return
            }

            // 重新渲染页面中的信息
            initBlogSettings()
            showMessage('保存成功')
        }).finally(()=>btnLoading.value=false)// 隐藏保存按钮loading
    })
}

//初始化博客设置数据并渲染到页面上
function initBlogSettings(){
    getBlogSettingDetail().then((e)=>{
        if(e.success == true){
            //设置表单数据
            form.name = e.data.name
            form.author = e.data.author
            form.logo = e.data.logo
            form.avatar = e.data.avatar
            form.introduction = e.data.introduction

            // 第三方平台信息设置，先判断后端返回平台链接是否为空，若不为空，则将 switch 组件置为选中状态，并设置表单对应数据
            if(e.data.githubHomepage){
                isGithubChecked.value = true
                form.githubHomepage = e.data.githubHomepage
            }
    
            if (e.data.giteeHomepage) {
                isGiteeChecked.value = true
                form.giteeHomepage = e.data.giteeHomepage
            }

            if (e.data.zhihuHomepage) {
                isZhihuChecked.value = true
                form.zhihuHomepage = e.data.zhihuHomepage
            }

            if (e.data.csdnHomepage) {
                isCSDNChecked.value = true
                form.csdnHomepage = e.data.csdnHomepage
            }
        }
    })
}
// 手动调用一下初始化方法
initBlogSettings()
</script>
<style>
/* 解决 textarea :focus 状态下，边框消失的问题 */
.el-textarea__inner:focus {
    outline: 0 !important;
    box-shadow: 0 0 0 1px var(--el-input-focus-border-color) inset !important;
}

.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    text-align: center;
}
</style>
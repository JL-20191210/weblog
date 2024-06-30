<template>
    <!-- 设置背景色为白色、高度为 64px，padding-right 为 4， border-bottom 为 slate 200 -->
    <div class="bg-white h-[64px] pr-4 flex border-b border-slate-100">
        <!-- 左边栏收缩、展开 -->
        <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200"
            @click="handleMenuWidth">
            <el-icon>
                <Fold v-if="menuStore.menuWidth == '250px'" />
                <Expand v-else />
            </el-icon>
        </div>

        <!-- 右边容器 通过ml-auto让其在父容器的右边 -->
        <div class="ml-auto flex">
            <!-- 点击刷新页面 -->
            <el-tooltip class="box-item" effect="dark" content="刷新" placement="bottom">
                <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 mr-2 hover:bg-gray-200"
                    @click="handleRefresh">
                    <el-icon>
                        <Refresh />
                    </el-icon>
                </div>
            </el-tooltip>
            <!-- 点击全屏显示 -->
            <el-tooltip class="box-item" effect="dark" content="全屏" placement="bottom">
                <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 mr-2 hover:bg-gray-200"
                    @click="toggle">
                    <el-icon>
                        <FullScreen v-if="!isFullscreen" />
                        <Aim v-else />
                    </el-icon>
                </div>
            </el-tooltip>

            <!-- 登录用户头像 -->
            <el-dropdown class="flex items-center justify-center" @command="handelCommand">
                <span class="el-dropdown-link flex items-center justify-center text-gray-700 text-xs">
                    <!-- 头像Avater -->
                    <el-avatar :size="25" src="https://img.picui.cn/free/2024/06/30/66810b089f214.jpg" />
                    {{ userStore.userInfo.username }}
                    <el-icon class="el-icon--right">
                        <arrow-down />
                    </el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item command="updatePassword">修改密码</el-dropdown-item>
                        <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>

            <el-dialog v-model="dialogVisible" title="修改密码" width="40%" :draggable="true" :close-on-click-modal="false"
                :close-on-press-escape="false">
                <el-form ref="formRef" :model="form" :rules="rules">
                    <el-form-item label="用户名" prop="username" label-width="120px">
                        <el-input size="large" v-model="form.username" placeholder="请输入用户名" clearable disabled />
                    </el-form-item>
                    <el-form-item label="密码" prop="password" label-width="120px">
                        <el-input size="large" type="password" v-model="form.password" placeholder="请输入密码" clearable
                            show-password />
                    </el-form-item>
                    <el-form-item label="确认密码" prop="rePassword" label-width="120px">
                        <el-input size="large" type="password" v-model="form.rePassword" placeholder="请确认密码" clearable
                            show-password />
                    </el-form-item>
                </el-form>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="dialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="onSubmit">
                            提交
                        </el-button>
                    </div>
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script setup>
// 引入 useFullscreen
import { ref, reactive, watch } from 'vue'
import { useFullscreen } from '@vueuse/core'
import { useMenuStore } from '@/stores/menu'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import { showMessage, showModel } from '@/composables/util'
import { updateAdminPassword } from '@/api/admin/user'

// isFullscreen 表示当前是否处于全屏；toggle 用于动态切换全屏、非全屏
const { isFullscreen, toggle } = useFullscreen()

const menuStore = useMenuStore()
const userStore = useUserStore()
const router = useRouter()
//对话框是否显示
const dialogVisible = ref(false)
// 表单引用
const formRef = ref(null)

// 修改用户密码表单对象
const form = reactive({
    username: userStore.userInfo.username || '',
    password: '',
    rePassword: ''
})

// 规则校验
const rules = {
    username: [
        {
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
        }
    ],
    password: [
        {
            required: true,
            message: '密码不能为空',
            trigger: 'blur'
        }
    ],
    rePassword: [
        {
            required: true,
            message: '确认密码不能为空',
            trigger: 'blur'
        }
    ]
}

// 下拉菜单事件处理
const handelCommand = (command) => {
    if (command == 'updatePassword') {
        //修改密码
        dialogVisible.value = true
    } else if (command == 'logout') {
        logout()
    }
}

const handleMenuWidth = () => {
    menuStore.handleMenuWidth()
}

const handleRefresh = () => location.reload()

function logout() {
    showModel('是否确认要退出登录？').then(() => {
        userStore.logout()
        showMessage('退出登录成功！')
        router.push('/login')
    })
}

const onSubmit = () => {
    // 先验证form表单字段
    formRef.value.validate((valid) => {
        if (!valid) {
            console.log('表单验证不通过');
            return false
        }

        if (form.password != form.rePassword) {
            showMessage('两次密码输入不一致，请检查', 'warning');
            return
        }

        updateAdminPassword(form).then((res) => {
            console.log('form', form.password)
            console.log(res);
            if (res.success == true) {
                showMessage('密码重置成功，请重新登录！')

                // 退出登录
                userStore.logout()

                // 隐藏对话框
                dialogVisible = false

                // 跳转登录页
                router.push('/login')
            } else {
                // 获取服务端返回的错误消息
                let message = res.message
                // 提示消息
                showMessage(message, 'error')
            }
        })
    })
}

// 监听 Pinia store 中的某个值的变化
watch(() => userStore.userInfo.username, (newValue, oldValue) => {
    console.log('新值', newValue);
    console.log('旧值', oldValue);

    // 可以在这里执行任何你需要的逻辑
    // 重新将新的值，设置会 form 对象中
    form.username = newValue
})

</script>
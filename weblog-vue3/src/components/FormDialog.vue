<template>
    <el-dialog v-model="dialogVisible" :title="title" :width="width" :destroy-on-close="destroyOnClose"  :draggable="true" :close-on-click-modal="false"
        :close-on-press-escape="false">
        <!-- 插槽 -->
        <slot></slot>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submit" :loading="btnLoading">
                    {{confirmText}}
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>


<script setup>
import { ref } from 'vue'
const dialogVisible = ref(false)

// 确认按钮加载loading
const btnLoading = ref(false)
// 显示loading
const showBtnLoading = ()=>btnLoading.value = true
// 关闭loading
const closeBtnLoading = ()=>btnLoading.value = false


// 打开
const open = () => dialogVisible.value = true
// 关闭
const close = () => dialogVisible.value = false

// 暴露给父组件
defineExpose({
    open,
    close,
    showBtnLoading,
    closeBtnLoading
})

// 对外暴露属性
const props = defineProps({
    title: String,
    width:{
        type:String,
        default:'40%'
    },
    destroyOnClose:{
        type:Boolean,
        default:false
    },
    confirmText:{
        type:String,
        default:'提交'
    }
})

// 对外暴露一个submit方法
const emit = defineEmits(['submit'])
//'submit'是子组件的事件，submit是暴露出去让父组件进行监听，使用emit函数声明子组件发出的事件。简言之，使用emit可以让父组件监听到子组件中的提交按钮被点击了
const submit = ()=>emit('submit')
</script>
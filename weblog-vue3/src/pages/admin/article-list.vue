<template>
    <div>
        <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
        <el-card shadow="never" class="md-5">
            <!-- flex 布局，内容垂直居中 -->
            <div class="flex items-center">
                <el-text>文章标题</el-text>
                <div class="ml-3 w-52 mr-5"><el-input v-model="searchArticleTitle" placeholder="请输入（模糊查询）"></el-input>
                </div>

                <el-text>创建日期</el-text>
                <div class="ml-3 w-30 mr-5">
                    <!-- 日期选择组件（区间选择） -->
                    <el-date-picker v-model="pickDate" type="daterange" range-separator="至" start-placeholder="开始时间"
                        end-placeholder="结束时间" :shortcuts="shortcuts" size="default"
                        @change="datepickerChange"></el-date-picker>
                </div>

                <el-button type="primary" class="ml-3" :icon="Search" @click="getTableDate">查询</el-button>
                <el-button class="ml-3" :icon="RefreshRight" @click="reset">重置</el-button>
            </div>
        </el-card>

        <el-card shadow="never">
            <!-- 写文章按钮 -->
            <div class="mb-5">
                <el-button type="primary" @click="isArticlePublishEditorShow = true">
                    <el-icon class="mr-1">
                        <EditPen />
                    </el-icon>写文章
                </el-button>
            </div>

            <!-- 分页列表 -->
            <el-table :data="tableData" border stripe style="width: 100%" v-loading="tableLoading">
                <el-table-column prop="title" label="标题" width="180" />
                <el-table-column prop="cover" label="封面" width="180">
                    <template #default="scope">
                        <el-image style="width: 50px;" :src="scope.row.cover"></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="发布时间" width="180" />
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button size="small">
                            <el-icon class="mr-1">
                                <Edit />
                            </el-icon>
                            编辑
                        </el-button>
                        <el-button type="danger" size="small" @click="deleteArticleSubmit(scope.row)">
                            <el-icon class="mr-1">
                                <Delete />
                            </el-icon>
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <el-dialog v-model="isArticlePublishEditorShow" :show-close="false" :fullscreen="true">
            <template #header="{ close, titleId, titleClass }">
                <!-- 固钉组件，固钉到顶部 -->
                <el-affix :offset="20" style="width: 100%;">
                    <!-- 指定 flex 布局， 高度为 10， 背景色为白色 -->
                    <div class="flex h-10 bg-white">
                        <!-- 字体加粗 -->
                        <h4 class="font-bold">写文章</h4>
                        <!-- 靠右对齐 -->
                        <div class="ml-auto flex">
                            <el-button @click="isArticlePublishEditorShow = false">取消</el-button>
                            <el-button type="primary">
                                <el-icon class="mr-1">
                                    <Promotion />
                                </el-icon>
                                发布
                            </el-button>
                        </div>
                    </div>
                </el-affix>
            </template>
            <!-- label-position="top" 用于指定 label 元素在上面 -->
            <el-form :model="form" ref="publishArticleFormRef" label-position="top" size="large" :rules="rules">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="form.title" autocomplete="off" size="large" maxlength="40" clearable />
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <!-- Markdown 编辑器 -->
                    <MdEditor v-model="form.content" @onUploadImg="onUploadImg" editorId="publishArticleEditor"></MdEditor>
                </el-form-item>
                <el-form-item label="封面" prop="cover">
                    <el-upload class="avatar-uploader" action="#" :on-change="handleCoverChange" :auto-upload="false"
                        :show-file-list="false">
                        <img v-if="form.cover" :src="form.cover" class="avatar">
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="摘要" prop="summary">
                    <!-- :rows="3" 指定 textarea 默认显示 3 行 -->
                    <el-input v-model="form.summary" :rows="3" type="textarea" placeholder="请输入文章摘要" />
                </el-form-item>
                <el-form-item label="分类" prop="categoryId">
                    <el-select v-model="form.categoryId" clearable placeholder="---请选择---">
                    </el-select>
                </el-form-item>
                <el-form-item label="标签" prop="tags">
                    <!-- 标签选择 -->
                    <el-select v-model="form.tags" multiple filterable remote reserve-keyword placeholder="---请输入---"
                        remote-show-suffix :remote-method="remoteMethod" allow-create default-first-option
                        :loading="tagSelectLoading" size="large">
                    </el-select>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
    <!-- 分页 -->
    <div class="mt-10 flex justify-center">
        <el-pagination v-model:current-page="current" v-model:page-size="size" :page-sizes="[10, 20, 50]" :small="false"
            :background="true" layout="total, sizes, prev, pager, next, jumper" :total="total"
            @size-change="handleSizeChange" @current-change="getTableDate" />
    </div>
</template>

<script setup>
// 引入所需图标
import { reactive, ref } from 'vue'
import { Search, RefreshRight, Promotion } from '@element-plus/icons-vue'
import moment from 'moment'
import { getArticlePageList, deleteArticle } from '@/api/admin/article'
import { showMessage, showModel } from '@/composables/util'
import { MdEditor } from 'md-editor-v3'
import { uploadFile } from '@/api/admin/file'
import 'md-editor-v3/lib/style.css'

// 模糊搜索的文章标题
const searchArticleTitle = ref('')
// 日期
const pickDate = ref('')
// 表格加载loading
const tableLoading = ref(false)
// 当前页码，给了一个默认值 1
const current = ref(1)
// 总数据量，给了个默认值 0
const total = ref(0)
// 每页显示的数据量，给了个默认值 10
const size = ref(10)
// 查询条件 起止时间
const startDate = reactive({})
const endDate = reactive({})
// 是否显示文章发布对话框
const isArticlePublishEditorShow = ref(false)
// 发布文章表单引用
const publishArticleFormRef = ref(null)

//表单对象
const form = reactive({
    id: null,
    title: '',
    content: '',
    cover: '',
    categoryId: null,
    tags: [],
    summary: ""
})

const rules = {
    title: [
        { required: true, message: '请输入文章标题', trigger: 'blur' },
        { min: 1, max: 40, message: '文章标题要求大于一个字符，小于40个字符' },
    ],
    content: [{ required: true }],
    cover: [{ required: true }],
    categoryId: [{ required: true, message: '请选择文章分类', trigger: 'blur' }],
    tags: [{ required: true, message: '请选择文章分标签', trigger: 'blur' }]
}

// 监听日期组件改变事件，并将开始结束时间设置到变量中
const datepickerChange = (e) => {
    startDate.value = moment(e[0]).format('YYYY-MM-DD')
    endDate.value = moment(e[1]).format('YYYY-MM-DD')

    console.log('开始时间：' + startDate.value + ', 结束时间：' + endDate.value)
}

const tableData = ref([])

const shortcuts = [
    {
        text: '最近一周',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            return [start, end]
        }
    },
    {
        text: '最近一个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            return [start, end]
        }
    },
    {
        text: '最近三个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            return [start, end]
        }
    },
]

const reset = () => {
    searchArticleTitle.value = '',
        pickDate.value = '',
        startDate.value = null,
        endDate.value = null
}

const deleteArticleSubmit = (row) => {
    console.log(row);
    showModel('是否确认要删除该文章？').then(() => {
        deleteArticle(row.id).then((res) => {
            if (res.success == false) {
                let message = res.message;
                showMessage(message, 'error');
                return
            }
            showMessage('删除成功')
            getTableDate()
        })
    }).catch(() => {
        console.log('取消了');
    })
}

// 上传文章封面图片
const handleCoverChange = (file) => {
    // 表单对象
    let formData = new FormData()
    // 添加 file 字段，并将文件传入 
    formData.append('file', file.raw)
    uploadFile(formData).then((e) => {
        // 响参失败，提示错误消息
        if (e.success == false) {
            let message = e.message
            showMessage(message, 'error')
            return
        }

        // 成功则设置表单对象中的封面链接，并提示上传成功
        form.cover = e.data.url
        showMessage('上传成功')
    })
}

const onUploadImg = async(files,callback)=>{
    const res = await Promise.all(
        files.map((file)=>{
            return new Promise((rev,rej)=>{
                console.log('==>编辑器开始上传文件...');
                let  formData = new FormData()
                formData.append('file',file)
                uploadFile(formData).then((res)=>{
                    console.log(res);
                    console.log('访问路径：'+res.data.url);
                    // 调用 callback 函数，回显上传图片(编辑器提供的)
                    callback([res.data.url])
                })
            })
        })
    )
}

// 获取分页数据
function getTableDate() {
    // 显示表格loading
    tableLoading.value = true
    // 调用后台分页接口，并传入所需参数
    getArticlePageList({ current: current.value, size: size.value, startDate: startDate.value, endDate: endDate.value, title: searchArticleTitle.value, })
        .then((res) => {
            if (res.success == true) {
                tableData.value = res.data
                current.value = res.current
                size.value = res.size
                total.value = res.total
            }
        }).finally(() => { tableLoading.value = false })//隐藏表格loading
}

getTableDate()

const handleSizeChange = (chooseSize) => {
    size.value = chooseSize
    getTableDate
}

</script>

<style scoped>
/* 封面图片样式 */
.avatar-uploader .avatar {
    width: 200px;
    height: 100px;
    display: block;
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 100px;
    text-align: center;
}
</style>
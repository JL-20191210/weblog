<template>
    <!-- 设置背景色为白色、高度为 64px，padding-right 为 4， border-bottom 为 slate 200 -->
    <div class="bg-white h-[64px] pr-4 flex border-b border-slate-200">
        <!-- 左边栏收缩、展开 -->
        <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200">
            <el-icon><Fold /></el-icon>
        </div>

        <!-- 右边容器 通过ml-auto让其在父容器的右边 -->
        <div class="ml-auto flex">
            <!-- 点击全屏显示 -->
            <el-tooltip class="box-item" effect="dark" content="全屏" placement="bottom">
                <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 mr-2 hover:bg-gray-200">
                    <el-icon>
                        <FullScreen/>
                    </el-icon>
                </div>
            </el-tooltip>

            <!-- 登录用户头像 -->
            <el-dropdown>
            <span class="el-dropdown-link flex items-center justify-center text-gray-700 text-xs">
                <!-- 头像Avater -->
                <el-avatar :size="25" src="https://img.quanxiaoha.com/quanxiaoha/f97361c0429d4bb1bc276ab835843065.jpg"/>
                Admin
                <el-icon class="el-icon--right">
                <arrow-down />
                </el-icon>
            </span>
            <template #dropdown>
                <el-dropdown-menu>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item>退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </template>
            </el-dropdown> 
        </div>
    </div>
</template>

  
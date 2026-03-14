<template>
  <ElDialog
    v-model="dialogVisible"
    :title="dialogType === 'add' ? '添加应用' : '编辑应用'"
    width="500px"
    align-center
  >
    <ElForm ref="formRef" :model="formData" :rules="rules" label-width="100px">
      <ElFormItem label="应用名称" prop="name">
        <ElInput v-model="formData.name" placeholder="请输入应用名称" />
      </ElFormItem>
      <ElFormItem label="操作系统" prop="osType">
        <ElSelect v-model="formData.osType" placeholder="请选择操作系统" :disabled="dialogType === 'edit'">
          <ElOption label="Android" :value="1" />
          <ElOption label="iOS" :value="2" />
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="接入方式" prop="accessType">
        <ElSelect v-model="formData.accessType" placeholder="请选择接入方式">
          <ElOption label="API" :value="1" />
          <ElOption label="SDK" :value="2" />
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="包名" prop="pkg">
        <ElInput v-model="formData.pkg" placeholder="请输入包名" />
      </ElFormItem>
      <ElFormItem label="下载地址" prop="downloadUrl">
        <ElInput v-model="formData.downloadUrl" placeholder="请输入下载地址" />
      </ElFormItem>
      <ElFormItem label="备注" prop="remark">
        <ElInput v-model="formData.remark" type="textarea" placeholder="请输入备注" />
      </ElFormItem>
    </ElForm>
    <template #footer>
      <div class="dialog-footer">
        <ElButton @click="dialogVisible = false">取消</ElButton>
        <ElButton type="primary" :loading="submitting" @click="handleSubmit">提交</ElButton>
      </div>
    </template>
  </ElDialog>
</template>

<script setup lang="ts">
  import { fetchCreateApp, fetchUpdateApp } from '@/api/media-manage'
  import type { FormInstance, FormRules } from 'element-plus'

  interface Props {
    visible: boolean
    type: string
    appData?: Partial<Api.MediaManage.AppListItem>
  }

  interface Emits {
    (e: 'update:visible', value: boolean): void
    (e: 'submit'): void
  }

  const props = defineProps<Props>()
  const emit = defineEmits<Emits>()

  const dialogVisible = computed({
    get: () => props.visible,
    set: (value) => emit('update:visible', value)
  })

  const dialogType = computed(() => props.type)
  const formRef = ref<FormInstance>()
  const submitting = ref(false)

  // 获取当前登录的媒体用户信息
  const getCurrentMediaId = (): number | undefined => {
    try {
      const mediaUserInfo = localStorage.getItem('media_user_info')
      if (mediaUserInfo) {
        const userInfo = JSON.parse(mediaUserInfo)
        return userInfo.id
      }
    } catch (error) {
      console.error('Failed to get media user info:', error)
    }
    return undefined
  }

  const formData = reactive({
    id: undefined as number | undefined,
    name: '',
    osType: 1,
    accessType: 2,
    pkg: '',
    downloadUrl: '',
    remark: ''
  })

  const rules: FormRules = {
    name: [
      { required: true, message: '请输入应用名称', trigger: 'blur' },
      { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
    ],
    osType: [{ required: true, message: '请选择操作系统', trigger: 'change' }]
  }

  const initFormData = () => {
    const isEdit = props.type === 'edit' && props.appData
    const row = props.appData

    Object.assign(formData, {
      id: isEdit && row ? row.id : undefined,
      name: isEdit && row ? row.name || '' : '',
      osType: isEdit && row ? row.osType || 1 : 1,
      accessType: isEdit && row ? row.accessType || 2 : 2,
      pkg: isEdit && row ? row.pkg || '' : '',
      downloadUrl: isEdit && row ? row.downloadUrl || '' : '',
      remark: isEdit && row ? row.remark || '' : ''
    })
  }

  watch(
    () => [props.visible, props.type, props.appData],
    ([visible]) => {
      if (visible) {
        initFormData()
        nextTick(() => {
          formRef.value?.clearValidate()
        })
      }
    },
    { immediate: true }
  )

  const handleSubmit = async () => {
    if (!formRef.value) return

    await formRef.value.validate(async (valid) => {
      if (valid) {
        submitting.value = true
        try {
          const mediaId = getCurrentMediaId()

          if (!mediaId) {
            ElMessage.error('未获取到媒体用户信息，请重新登录')
            return
          }

          if (dialogType.value === 'add') {
            await fetchCreateApp({
              name: formData.name,
              osType: formData.osType,
              accessType: formData.accessType,
              pkg: formData.pkg,
              downloadUrl: formData.downloadUrl,
              remark: formData.remark
            })
            ElMessage.success('添加成功')
          } else {
            await fetchUpdateApp({
              id: formData.id!,
              name: formData.name,
              osType: formData.osType,
              accessType: formData.accessType,
              pkg: formData.pkg,
              downloadUrl: formData.downloadUrl,
              remark: formData.remark
            })
            ElMessage.success('更新成功')
          }
          dialogVisible.value = false
          emit('submit')
        } finally {
          submitting.value = false
        }
      }
    })
  }
</script>

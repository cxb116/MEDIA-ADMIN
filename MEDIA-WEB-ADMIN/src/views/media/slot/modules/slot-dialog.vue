<template>
  <ElDialog
    v-model="dialogVisible"
    :title="dialogType === 'add' ? '添加广告位' : '编辑广告位'"
    width="600px"
    align-center
  >
    <ElForm ref="formRef" :model="formData" :rules="rules" label-width="100px">
      <ElFormItem label="应用" prop="appId">
        <ElSelect
          v-model="formData.appId"
          placeholder="请选择应用"
          style="width: 100%"
          :loading="loadingApps"
        >
          <ElOption v-for="app in appList" :key="app.id" :label="app.name" :value="app.id" />
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="广告位名称" prop="name">
        <ElInput v-model="formData.name" placeholder="请输入广告位名称" />
      </ElFormItem>
      <ElFormItem label="广告类型" prop="adTypeId">
        <ElSelect
          v-model="formData.adTypeId"
          placeholder="请选择广告类型"
          style="width: 100%"
          :loading="loadingAdTypes"
          @change="handleAdTypeChange"
        >
          <ElOption v-for="type in adTypeList" :key="type.id" :label="type.name" :value="type.id" />
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="广告场景" prop="adSceneId">
        <ElSelect
          v-model="formData.adSceneId"
          placeholder="请先选择广告类型"
          style="width: 100%"
          :loading="loadingAdScenes"
          :disabled="!formData.adTypeId"
        >
          <ElOption
            v-for="scene in adSceneList"
            :key="scene.id"
            :label="scene.name"
            :value="scene.id"
          />
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="广告尺寸" prop="adSizeId">
        <ElSelect
          v-model="formData.adSizeId"
          placeholder="请先选择广告类型"
          style="width: 100%"
          :loading="loadingAdSizes"
          :disabled="!formData.adTypeId"
        >
          <ElOption v-for="size in adSizeList" :key="size.id" :label="size.size" :value="size.id" />
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="尺寸" prop="width">
        <ElRow :gutter="20">
          <ElCol :span="12">
            <ElInputNumber
              v-model="formData.width"
              :min="0"
              placeholder="宽度"
              style="width: 100%"
            />
          </ElCol>
          <ElCol :span="12">
            <ElInputNumber
              v-model="formData.height"
              :min="0"
              placeholder="高度"
              style="width: 100%"
            />
          </ElCol>
        </ElRow>
      </ElFormItem>
      <ElFormItem label="图片地址" prop="adImage">
        <ElInput v-model="formData.adImage" placeholder="请输入图片地址" />
      </ElFormItem>
      <ElFormItem label="交互类型" prop="interactionType">
        <ElCheckboxGroup v-model="interactionTypes">
          <ElCheckbox :value="1">打开网页</ElCheckbox>
          <ElCheckbox :value="2">Deeplink</ElCheckbox>
          <ElCheckbox :value="4">直接下载</ElCheckbox>
          <ElCheckbox :value="8">广点通</ElCheckbox>
          <ElCheckbox :value="16">小程序</ElCheckbox>
          <ElCheckbox :value="32">应用商店</ElCheckbox>
          <ElCheckbox :value="64">快应用</ElCheckbox>
        </ElCheckboxGroup>
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
  import {
    fetchCreateSlot,
    fetchUpdateSlot,
    fetchGetAppListByMediaId,
    fetchGetAdTypes,
    fetchGetAdScenes,
    fetchGetAdSizes
  } from '@/api/media-manage'
  import type { FormInstance, FormRules } from 'element-plus'

  interface Props {
    visible: boolean
    type: string
    slotData?: Partial<Api.MediaManage.SlotListItem>
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

  // 应用相关
  const loadingApps = ref(false)
  const appList = ref<Api.MediaManage.AppListItem[]>([])

  // 广告类型相关
  const loadingAdTypes = ref(false)
  const adTypeList = ref<Api.DspConfig.AdTypeResponse[]>([])

  // 广告场景相关
  const loadingAdScenes = ref(false)
  const adSceneList = ref<Api.DspConfig.AdSceneResponse[]>([])

  // 广告尺寸相关
  const loadingAdSizes = ref(false)
  const adSizeList = ref<Api.DspConfig.AdSizeResponse[]>([])

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

  // 加载应用列表
  const loadAppList = async () => {
    const mediaId = getCurrentMediaId()
    if (!mediaId) {
      ElMessage.warning('未获取到媒体用户信息')
      return
    }

    loadingApps.value = true
    try {
      const data = await fetchGetAppListByMediaId(mediaId)
      appList.value = data || []
    } catch (error) {
      console.error('Failed to load app list:', error)
      ElMessage.error('加载应用列表失败')
    } finally {
      loadingApps.value = false
    }
  }

  // 加载广告类型列表
  const loadAdTypes = async () => {
    loadingAdTypes.value = true
    try {
      const data = await fetchGetAdTypes()
      adTypeList.value = data || []
    } catch (error) {
      console.error('Failed to load ad types:', error)
      ElMessage.error('加载广告类型失败')
    } finally {
      loadingAdTypes.value = false
    }
  }

  // 加载广告场景列表
  const loadAdScenes = async (typeId: number) => {
    if (!typeId) {
      adSceneList.value = []
      return
    }

    loadingAdScenes.value = true
    try {
      const data = await fetchGetAdScenes(typeId)
      adSceneList.value = data || []
    } catch (error) {
      console.error('Failed to load ad scenes:', error)
      ElMessage.error('加载广告场景失败')
    } finally {
      loadingAdScenes.value = false
    }
  }

  // 加载广告尺寸列表
  const loadAdSizes = async (typeId: number) => {
    if (!typeId) {
      adSizeList.value = []
      return
    }

    loadingAdSizes.value = true
    try {
      const data = await fetchGetAdSizes(typeId)
      adSizeList.value = data || []
    } catch (error) {
      console.error('Failed to load ad sizes:', error)
      ElMessage.error('加载广告尺寸失败')
    } finally {
      loadingAdSizes.value = false
    }
  }

  // 广告类型改变时，重新加载场景和尺寸
  const handleAdTypeChange = (typeId: number) => {
    formData.adSceneId = undefined
    formData.adSizeId = undefined
    if (typeId) {
      loadAdScenes(typeId)
      loadAdSizes(typeId)
    } else {
      adSceneList.value = []
      adSizeList.value = []
    }
  }

  const formData = reactive({
    id: undefined as number | undefined,
    appId: undefined as number | undefined,
    name: '',
    adTypeId: undefined as number | undefined,
    adSceneId: undefined as number | undefined,
    adSizeId: undefined as number | undefined,
    width: 0,
    height: 0,
    adImage: '',
    interactionType: 0,
    remark: ''
  })

  const interactionTypes = ref<number[]>([])

  watch(interactionTypes, (vals) => {
    formData.interactionType = vals.reduce((sum, val) => sum + val, 0)
  })

  const rules: FormRules = {
    appId: [{ required: true, message: '请选择应用', trigger: 'change' }],
    name: [
      { required: true, message: '请输入广告位名称', trigger: 'blur' },
      { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
    ]
  }

  const initFormData = async () => {
    const isEdit = props.type === 'edit' && props.slotData
    const row = props.slotData

    Object.assign(formData, {
      id: isEdit && row ? row.id : undefined,
      appId: isEdit && row ? row.appId : undefined,
      name: isEdit && row ? row.name || '' : '',
      adTypeId: isEdit && row ? row.adTypeId || undefined : undefined,
      adSceneId: isEdit && row ? row.adSceneId || undefined : undefined,
      adSizeId: isEdit && row ? row.adSizeId || undefined : undefined,
      width: isEdit && row ? row.width || 0 : 0,
      height: isEdit && row ? row.height || 0 : 0,
      adImage: isEdit && row ? row.adImage || '' : '',
      interactionType: isEdit && row ? row.interactionType || 0 : 0,
      remark: isEdit && row ? row.remark || '' : ''
    })

    // 如果是编辑且有广告类型，加载对应的场景和尺寸
    if (isEdit && row && row.adTypeId) {
      await loadAdScenes(row.adTypeId)
      await loadAdSizes(row.adTypeId)
    }

    const types: number[] = []
    let interaction = formData.interactionType
    if (interaction & 1) types.push(1)
    if (interaction & 2) types.push(2)
    if (interaction & 4) types.push(4)
    if (interaction & 8) types.push(8)
    if (interaction & 16) types.push(16)
    if (interaction & 32) types.push(32)
    if (interaction & 64) types.push(64)
    interactionTypes.value = types
  }

  watch(
    () => [props.visible, props.type, props.slotData],
    async ([visible]) => {
      if (visible) {
        loadAppList()
        loadAdTypes()
        await initFormData()
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
            await fetchCreateSlot({
              mediaId: mediaId,
              appId: formData.appId!,
              name: formData.name,
              adTypeId: formData.adTypeId,
              adSceneId: formData.adSceneId,
              adSizeId: formData.adSizeId,
              width: formData.width,
              height: formData.height,
              adImage: formData.adImage,
              interactionType: formData.interactionType,
              remark: formData.remark
            })
            ElMessage.success('添加成功')
          } else {
            await fetchUpdateSlot({
              id: formData.id!,
              name: formData.name,
              adTypeId: formData.adTypeId,
              adSceneId: formData.adSceneId,
              adSizeId: formData.adSizeId,
              width: formData.width,
              height: formData.height,
              adImage: formData.adImage,
              interactionType: formData.interactionType,
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

<!-- 注册页面 -->
<template>
  <div class="flex w-full h-screen">
    <LoginLeftView />

    <div class="relative flex-1">
      <AuthTopBar />

      <div class="auth-right-wrap">
        <div class="form">
          <h3 class="title">{{ $t('register.title') }}</h3>
          <p class="sub-title">{{ $t('register.subTitle') }}</p>
          <ElForm
            class="mt-7.5"
            ref="formRef"
            :model="formData"
            :rules="rules"
            label-position="top"
            :key="formKey"
          >
            <ElFormItem prop="mediaCompanyName" label="公司名称">
              <ElInput
                class="custom-height"
                v-model.trim="formData.mediaCompanyName"
                placeholder="请输入公司名称"
              />
            </ElFormItem>

            <ElFormItem prop="name" label="媒体名称">
              <ElInput
                class="custom-height"
                v-model.trim="formData.name"
                placeholder="请输入媒体名称"
              />
            </ElFormItem>

            <ElFormItem prop="account" label="账号">
              <ElInput
                class="custom-height"
                v-model.trim="formData.account"
                placeholder="请输入账号（3-50位字母数字下划线）"
              />
            </ElFormItem>

            <ElFormItem prop="password" label="密码">
              <ElInput
                class="custom-height"
                v-model.trim="formData.password"
                type="password"
                autocomplete="off"
                show-password
                placeholder="请输入密码（6-20位）"
              />
            </ElFormItem>

            <ElFormItem prop="confirmPassword" label="确认密码">
              <ElInput
                class="custom-height"
                v-model.trim="formData.confirmPassword"
                type="password"
                autocomplete="off"
                show-password
                placeholder="请再次输入密码"
                @keyup.enter="register"
              />
            </ElFormItem>

            <ElFormItem prop="contactName" label="联系人">
              <ElInput
                class="custom-height"
                v-model.trim="formData.contactName"
                placeholder="请输入联系人"
              />
            </ElFormItem>

            <div style="margin-top: 15px">
              <ElButton
                class="w-full custom-height"
                type="primary"
                @click="register"
                :loading="loading"
                v-ripple
              >
                {{ $t('register.submitBtnText') }}
              </ElButton>
            </div>

            <div class="mt-5 text-sm text-g-600">
              <span>{{ $t('register.hasAccount') }}</span>
              <RouterLink class="text-theme" :to="{ name: 'Login' }">{{
                $t('register.toLogin')
              }}</RouterLink>
            </div>
          </ElForm>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { useI18n } from 'vue-i18n'
  import type { FormInstance, FormRules } from 'element-plus'
  import { fetchMediaRegister } from '@/api/mediaAuth'
  import { ElMessage } from 'element-plus'

  defineOptions({ name: 'Register' })

  interface RegisterForm {
    name: string
    account: string
    password: string
    confirmPassword: string
    mediaCompanyName: string
    contactName: string
  }

  const { t, locale } = useI18n()
  const router = useRouter()
  const formRef = ref<FormInstance>()

  const loading = ref(false)
  const formKey = ref(0)

  watch(locale, () => {
    formKey.value++
  })

  const formData = reactive<RegisterForm>({
    name: '',
    account: '',
    password: '',
    confirmPassword: '',
    mediaCompanyName: '',
    contactName: ''
  })

  const validatePassword = (_rule: any, value: string, callback: (error?: Error) => void) => {
    if (!value) {
      callback(new Error('请输入密码'))
      return
    }
    if (formData.confirmPassword) {
      formRef.value?.validateField('confirmPassword')
    }
    callback()
  }

  const validateConfirmPassword = (
    _rule: any,
    value: string,
    callback: (error?: Error) => void
  ) => {
    if (!value) {
      callback(new Error('请再次输入密码'))
      return
    }
    if (value !== formData.password) {
      callback(new Error('两次输入的密码不一致'))
      return
    }
    callback()
  }

  const validateAccount = (_rule: any, value: string, callback: (error?: Error) => void) => {
    if (!value) {
      callback(new Error('请输入账号'))
      return
    }
    if (!/^[a-zA-Z0-9_]+$/.test(value)) {
      callback(new Error('账号只能包含字母、数字和下划线'))
      return
    }
    callback()
  }

  const rules = computed<FormRules<RegisterForm>>(() => ({
    name: [
      { required: true, message: '请输入媒体名称', trigger: 'blur' }
    ],
    account: [
      { required: true, validator: validateAccount, trigger: 'blur' },
      { min: 3, max: 50, message: '账号长度必须在3-50位之间', trigger: 'blur' }
    ],
    password: [
      { required: true, validator: validatePassword, trigger: 'blur' },
      { min: 6, max: 20, message: '密码长度必须在6-20位之间', trigger: 'blur' }
    ],
    confirmPassword: [
      { required: true, validator: validateConfirmPassword, trigger: 'blur' }
    ]
  }))

  const register = async () => {
    if (!formRef.value) return

    try {
      await formRef.value.validate()
      loading.value = true

      const params: any = {
        name: formData.name,
        account: formData.account,
        password: formData.password,
        mediaCompanyName: formData.mediaCompanyName,
        contactName: formData.contactName
      }

      const data = await fetchMediaRegister(params)
      ElMessage.success(data?.message || '注册成功，请等待审核')
      toLogin()
    } catch (error: any) {
      console.error('注册失败:', error)
      ElMessage.error(error?.message || '注册失败，请稍后重试')
    } finally {
      loading.value = false
    }
  }

  const toLogin = () => {
    setTimeout(() => {
      router.push({ name: 'Login' })
    }, 1500)
  }
</script>

<style scoped>
  @import '../login/style.css';
  
  .auth-right-wrap {
    height: auto !important;
    max-height: 90vh;
    overflow-y: auto !important;
  }
</style>

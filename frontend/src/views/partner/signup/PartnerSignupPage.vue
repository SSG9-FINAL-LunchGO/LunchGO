<script setup>
import { ref, reactive } from 'vue';
import { RouterLink, useRouter } from 'vue-router'; // Import useRouter
import { ArrowLeft } from 'lucide-vue-next';

const router = useRouter(); // Initialize useRouter

const formData = reactive({
  username: '',
  password: '',
  passwordConfirm: '',
  name: '',
  businessNumber: '',
  phoneNumber: '',
  verificationCode: '',
  agreeTerms: false,
  agreePrivacy: false,
});

const isIdChecked = ref(false);
const isBusinessVerified = ref(false);
const isVerificationSent = ref(false);

const handleCheckId = () => {
  if (formData.username) {
    alert('사용 가능한 아이디입니다.');
    isIdChecked.value = true;
  }
};

const handleVerifyBusiness = () => {
  if (formData.businessNumber) {
    alert('사업자 등록번호가 인증되었습니다.');
    isBusinessVerified.value = true;
  }
};

const handleSendVerification = () => {
  if (formData.phoneNumber) {
    alert('인증번호가 발송되었습니다.');
    isVerificationSent.value = true;
  }
};

const handleSubmit = () => {
  if (!isIdChecked.value) {
    alert('아이디 중복확인을 해주세요.');
    return;
  }

  if (!isBusinessVerified.value) {
    alert('사업자 등록번호를 인증해주세요.');
    return;
  }

  if (!isVerificationSent.value) {
    alert('휴대폰 인증을 완료해주세요.');
    return;
  }

  if (formData.password !== formData.passwordConfirm) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }

  if (!formData.agreeTerms || !formData.agreePrivacy) {
    alert('약관에 동의해주세요.');
    return;
  }

  alert('회원가입이 완료되었습니다!');
  router.push('/business/dashboard'); // Use router.push for navigation
};
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa]">
    <!-- Header -->
    <header class="bg-white border-b border-[#e9ecef] px-4 py-3 flex items-center justify-between">
      <div class="flex items-center gap-3">
        <RouterLink to="/partner" class="text-[#1e3a5f]">
          <ArrowLeft class="w-5 h-5" />
        </RouterLink>
        <span class="text-lg font-semibold text-[#1e3a5f]">회원가입 - 사업자</span>
      </div>
      <div class="flex gap-4">
        <RouterLink to="/login" class="text-sm text-[#6c757d]">
          로그인
        </RouterLink>
        <span class="text-sm font-semibold gradient-primary-text">회원가입</span>
      </div>
    </header>

    <div class="px-4 py-8 max-w-2xl mx-auto">
      <h1 class="text-3xl font-bold text-[#1e3a5f] text-center mb-8">회원가입</h1>

      <form @submit.prevent="handleSubmit" class="space-y-6">
        <!-- 아이디 -->
        <div class="space-y-2">
          <label class="block text-sm font-semibold text-[#1e3a5f]">아이디</label>
          <div class="flex gap-2">
            <input
              type="text"
              v-model="formData.username"
              placeholder="아이디를 입력하세요."
              class="flex-1 px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:border-[#FF6B4A]"
            />
            <button
              type="button"
              @click="handleCheckId"
              class="px-6 py-3 bg-white border border-[#dee2e6] text-[#1e3a5f] rounded-lg hover:bg-[#f8f9fa] transition-colors whitespace-nowrap"
            >
              중복확인
            </button>
          </div>
        </div>

        <!-- 비밀번호 -->
        <div class="space-y-2">
          <label class="block text-sm font-semibold text-[#1e3a5f]">비밀번호</label>
          <input
            type="password"
            v-model="formData.password"
            placeholder="최소 8자, 최대 20자, 소문자,대문자,숫자,특수기호 포함"
            class="w-full px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:border-[#FF6B4A]"
          />
        </div>

        <!-- 비밀번호 확인 -->
        <div class="space-y-2">
          <label class="block text-sm font-semibold text-[#1e3a5f]">비밀번호 확인</label>
          <input
            type="password"
            v-model="formData.passwordConfirm"
            placeholder="비밀번호 재입력"
            class="w-full px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:border-[#FF6B4A]"
          />
        </div>

        <!-- 이름 -->
        <div class="space-y-2">
          <label class="block text-sm font-semibold text-[#1e3a5f]">이름</label>
          <input
            type="text"
            v-model="formData.name"
            placeholder="이름을 입력하세요."
            class="w-full px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:border-[#FF6B4A]"
          />
        </div>

        <!-- 사업자등록번호 -->
        <div class="space-y-2">
          <label class="block text-sm font-semibold text-[#1e3a5f]">사업자등록번호</label>
          <div class="flex gap-2">
            <input
              type="text"
              v-model="formData.businessNumber"
              placeholder="사업자 등록번호를 입력하세요."
              class="flex-1 px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:border-[#FF6B4A]"
            />
            <button
              type="button"
              @click="handleVerifyBusiness"
              class="px-6 py-3 bg-white border border-[#dee2e6] text-[#1e3a5f] rounded-lg hover:bg-[#f8f9fa] transition-colors whitespace-nowrap"
            >
              인증하기
            </button>
          </div>
        </div>

        <!-- 전화번호 -->
        <div class="space-y-2">
          <label class="block text-sm font-semibold text-[#1e3a5f]">전화번호</label>
          <div class="flex gap-2">
            <input
              type="tel"
              v-model="formData.phoneNumber"
              placeholder="전화번호를 입력하세요."
              class="flex-1 px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:border-[#FF6B4A]"
            />
            <button
              type="button"
              @click="handleSendVerification"
              class="px-6 py-3 bg-white border border-[#dee2e6] text-[#1e3a5f] rounded-lg hover:bg-[#f8f9fa] transition-colors whitespace-nowrap"
            >
              인증번호 발송
            </button>
          </div>
        </div>

        <!-- 인증번호 -->
        <div class="space-y-2">
          <input
            type="text"
            v-model="formData.verificationCode"
            placeholder="인증번호를 입력하세요."
            class="w-full px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:border-[#FF6B4A]"
          />
        </div>

        <!-- 약관 동의 -->
        <div class="space-y-3 pt-4">
          <label class="flex items-center gap-3 cursor-pointer">
            <input
              type="checkbox"
              v-model="formData.agreeTerms"
              class="hidden peer"
            />
            <div
              :class="`w-5 h-5 rounded border-2 flex items-center justify-center transition-all ${
                formData.agreeTerms ? 'gradient-primary border-transparent' : 'border-[#dee2e6]'
              }`"
            >
              <svg v-if="formData.agreeTerms" class="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7" />
              </svg>
            </div>
            <span class="text-sm text-[#1e3a5f]">서비스 이용약관 동의 [필수]</span>
          </label>

          <label class="flex items-center gap-3 cursor-pointer">
            <input
              type="checkbox"
              v-model="formData.agreePrivacy"
              class="hidden peer"
            />
            <div
              :class="`w-5 h-5 rounded border-2 flex items-center justify-center transition-all ${
                formData.agreePrivacy ? 'gradient-primary border-transparent' : 'border-[#dee2e6]'
              }`"
            >
              <svg v-if="formData.agreePrivacy" class="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7" />
              </svg>
            </div>
            <span class="text-sm text-[#1e3a5f]">개인정보 처리방침 동의 [필수]</span>
          </label>
        </div>

        <!-- 회원가입 버튼 -->
        <button
          type="submit"
          class="w-full gradient-primary text-white py-4 rounded-xl font-bold text-lg hover:opacity-90 transition-opacity mt-6"
        >
          회원가입
        </button>
      </form>
    </div>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
.gradient-primary-text {
  background: linear-gradient(135deg, #ff6b4a 0%, #ffaa8d 50%, #ffc4b8 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-fill-color: transparent;
}
</style>

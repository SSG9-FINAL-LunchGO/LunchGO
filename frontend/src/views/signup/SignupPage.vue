<script setup>
import { ref } from 'vue';
import { RouterLink } from 'vue-router';
import { ArrowLeft } from 'lucide-vue-next';
import Button from '@/components/ui/Button.vue';
import Card from '@/components/ui/Card.vue';
import Input from '@/components/ui/Input.vue';

const name = ref('');
const email = ref('');
const phone = ref('');
const verificationCode = ref('');
const password = ref('');
const passwordConfirm = ref('');
const agreeTerms = ref(false);
const agreePrivacy = ref(false);
const agreeMarketing = ref(false);

const handleEmailDuplicateCheck = () => {
  alert('이메일 중복 확인');
  // Logic for email duplicate check
};

const handleSendVerificationCode = () => {
  alert('인증번호 발송');
  // Logic to send verification code
};

const handleSignup = () => {
  // Basic validation (more comprehensive validation would be needed)
  if (password.value !== passwordConfirm.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }
  if (!agreeTerms.value || !agreePrivacy.value) {
    alert('필수 약관에 동의해주세요.');
    return;
  }
  alert('회원가입 완료!');
  // Logic for signup
};
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa] flex flex-col">
    <!-- Header -->
    <header class="bg-white border-b border-[#e9ecef]">
      <div class="max-w-[500px] mx-auto px-4 h-14 flex items-center">
        <RouterLink to="/" class="flex items-center gap-2 text-[#495057] hover:text-[#1e3a5f]">
          <ArrowLeft class="w-5 h-5" />
          <span class="text-sm font-medium">돌아가기</span>
        </RouterLink>
      </div>
    </header>

    <!-- Main Content -->
    <main class="flex-1 max-w-[500px] mx-auto w-full px-4 py-8">
      <div class="flex flex-col items-center mb-8">
        <img src="/images/lunch-go-whitebg.png" alt="런치고" width="80" height="80" class="w-20 h-20 mb-4" />
        <h1 class="text-2xl font-bold text-[#1e3a5f] mb-2">회원가입</h1>
        <p class="text-sm text-[#6c757d]">런치고 회원이 되어 편리한 회식 예약을 경험하세요</p>
      </div>

      <Card class="border-[#e9ecef] rounded-xl bg-white shadow-card p-6">
        <form @submit.prevent="handleSignup" class="space-y-4">
          <div>
            <label for="name" class="block text-sm font-medium text-[#1e3a5f] mb-2">
              이름
            </label>
            <Input
              id="name"
              type="text"
              placeholder="이름을 입력하세요"
              v-model="name"
              class="w-full h-12 px-4 border-[#dee2e6] rounded-lg focus:border-[#ff6b4a] focus:ring-1 focus:ring-[#ff6b4a] text-[#1e3a5f]"
            />
          </div>

          <div>
            <label for="email" class="block text-sm font-medium text-[#1e3a5f] mb-2">
              이메일
            </label>
            <div class="flex gap-2">
              <Input
                id="email"
                type="email"
                placeholder="example@email.com"
                v-model="email"
                class="flex-1 h-12 px-4 border-[#dee2e6] rounded-lg focus:border-[#ff6b4a] focus:ring-1 focus:ring-[#ff6b4a] text-[#1e3a5f]"
              />
              <Button
                type="button"
                @click="handleEmailDuplicateCheck"
                variant="outline"
                class="h-12 px-4 border-[#dee2e6] text-[#495057] bg-white hover:bg-[#f8f9fa] rounded-lg whitespace-nowrap"
              >
                중복확인
              </Button>
            </div>
          </div>

          <div>
            <label for="phone" class="block text-sm font-medium text-[#1e3a5f] mb-2">
              휴대폰 번호
            </label>
            <div class="flex gap-2">
              <Input
                id="phone"
                type="tel"
                placeholder="01012345678"
                v-model="phone"
                class="flex-1 h-12 px-4 border-[#dee2e6] rounded-lg focus:border-[#ff6b4a] focus:ring-1 focus:ring-[#ff6b4a] text-[#1e3a5f]"
              />
              <Button
                type="button"
                @click="handleSendVerificationCode"
                variant="outline"
                class="h-12 px-4 border-[#dee2e6] text-[#495057] bg-white hover:bg-[#f8f9fa] rounded-lg whitespace-nowrap"
              >
                인증번호
              </Button>
            </div>
          </div>

          <div>
            <label for="verify" class="block text-sm font-medium text-[#1e3a5f] mb-2">
              인증번호
            </label>
            <Input
              id="verify"
              type="text"
              placeholder="인증번호를 입력하세요"
              v-model="verificationCode"
              class="w-full h-12 px-4 border-[#dee2e6] rounded-lg focus:border-[#ff6b4a] focus:ring-1 focus:ring-[#ff6b4a] text-[#1e3a5f]"
            />
          </div>

          <div>
            <label for="password" class="block text-sm font-medium text-[#1e3a5f] mb-2">
              비밀번호
            </label>
            <Input
              id="password"
              type="password"
              placeholder="비밀번호를 입력하세요 (8자 이상)"
              v-model="password"
              class="w-full h-12 px-4 border-[#dee2e6] rounded-lg focus:border-[#ff6b4a] focus:ring-1 focus:ring-[#ff6b4a] text-[#1e3a5f]"
            />
          </div>

          <div>
            <label for="password-confirm" class="block text-sm font-medium text-[#1e3a5f] mb-2">
              비밀번호 확인
            </label>
            <Input
              id="password-confirm"
              type="password"
              placeholder="비밀번호를 다시 입력하세요"
              v-model="passwordConfirm"
              class="w-full h-12 px-4 border-[#dee2e6] rounded-lg focus:border-[#ff6b4a] focus:ring-1 focus:ring-[#ff6b4a] text-[#1e3a5f]"
            />
          </div>

          <div class="space-y-2 pt-2">
            <label class="flex items-center gap-2 text-sm text-[#495057] cursor-pointer">
              <input
                type="checkbox"
                v-model="agreeTerms"
                class="w-4 h-4 rounded border-[#dee2e6] text-[#ff6b4a] focus:ring-[#ff6b4a]"
              />
              <span>
                <span class="text-[#ff6b4a] font-medium">(필수)</span> 서비스 이용약관 동의
              </span>
            </label>
            <label class="flex items-center gap-2 text-sm text-[#495057] cursor-pointer">
              <input
                type="checkbox"
                v-model="agreePrivacy"
                class="w-4 h-4 rounded border-[#dee2e6] text-[#ff6b4a] focus:ring-[#ff6b4a]"
              />
              <span>
                <span class="text-[#ff6b4a] font-medium">(필수)</span> 개인정보 처리방침 동의
              </span>
            </label>
            <label class="flex items-center gap-2 text-sm text-[#495057] cursor-pointer">
              <input
                type="checkbox"
                v-model="agreeMarketing"
                class="w-4 h-4 rounded border-[#dee2e6] text-[#ff6b4a] focus:ring-[#ff6b4a]"
              />
              <span>(선택) 마케팅 정보 수신 동의</span>
            </label>
          </div>

          <Button
            type="submit"
            class="w-full h-12 gradient-primary text-white font-semibold rounded-xl hover:opacity-90 transition-opacity shadow-button mt-6"
          >
            회원가입
          </Button>
        </form>

        <div class="mt-6 text-center">
          <p class="text-sm text-[#6c757d]">
            이미 회원이신가요?
            <RouterLink to="/login" class="text-[#ff6b4a] font-semibold hover:underline">
              로그인
            </RouterLink>
          </p>
        </div>
      </Card>

      <div class="mt-6">
        <div class="relative">
          <div class="absolute inset-0 flex items-center">
            <div class="w-full border-t border-[#dee2e6]"></div>
          </div>
          <div class="relative flex justify-center text-sm">
            <span class="px-4 bg-[#f8f9fa] text-[#6c757d]">또는</span>
          </div>
        </div>

        <div class="mt-6 space-y-3">
          <Button
            type="button"
            variant="outline"
            class="w-full h-12 border-[#dee2e6] bg-white text-[#1e3a5f] font-medium rounded-xl hover:bg-[#f8f9fa] transition-colors"
          >
            카카오로 회원가입
          </Button>
          <Button
            type="button"
            variant="outline"
            class="w-full h-12 border-[#dee2e6] bg-white text-[#1e3a5f] font-medium rounded-xl hover:bg-[#f8f9fa] transition-colors"
          >
            네이버로 회원가입
          </Button>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>

<script setup>
import { ref } from 'vue';
import { RouterLink } from 'vue-router';
import { ArrowLeft } from 'lucide-vue-next';
import Button from '@/components/ui/Button.vue';
import Card from '@/components/ui/Card.vue';
import Input from '@/components/ui/Input.vue'; // Assuming Input.vue is exported as default

const email = ref('');
const password = ref('');
const rememberMe = ref(false); // For "로그인 유지" checkbox

const handleLogin = () => {
  // Implement login logic here
  console.log('Login attempt with:', email.value, password.value, 'Remember me:', rememberMe.value);
  // Example: router.push('/dashboard') after successful login
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
        <h1 class="text-2xl font-bold text-[#1e3a5f] mb-2">로그인</h1>
        <p class="text-sm text-[#6c757d]">런치고에 오신 것을 환영합니다</p>
      </div>

      <Card class="border-[#e9ecef] rounded-xl bg-white shadow-card p-6">
        <form @submit.prevent="handleLogin" class="space-y-4">
          <div>
            <label for="email" class="block text-sm font-medium text-[#1e3a5f] mb-2">
              이메일
            </label>
            <Input
              id="email"
              type="email"
              placeholder="example@email.com"
              v-model="email"
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
              placeholder="비밀번호를 입력하세요"
              v-model="password"
              class="w-full h-12 px-4 border-[#dee2e6] rounded-lg focus:border-[#ff6b4a] focus:ring-1 focus:ring-[#ff6b4a] text-[#1e3a5f]"
            />
          </div>

          <div class="flex items-center justify-between text-sm">
            <label class="flex items-center gap-2 text-[#495057] cursor-pointer">
              <input
                type="checkbox"
                v-model="rememberMe"
                class="w-4 h-4 rounded border-[#dee2e6] text-[#ff6b4a] focus:ring-[#ff6b4a]"
              />
              <span>로그인 유지</span>
            </label>
            <button type="button" class="text-[#6c757d] hover:text-[#ff6b4a] transition-colors">
              비밀번호 찾기
            </button>
          </div>

          <Button
            type="submit"
            class="w-full h-12 gradient-primary text-white font-semibold rounded-xl hover:opacity-90 transition-opacity shadow-button"
          >
            로그인
          </Button>
        </form>

        <div class="mt-6 text-center">
          <p class="text-sm text-[#6c757d]">
            아직 회원이 아니신가요?
            <RouterLink to="/signup" class="text-[#ff6b4a] font-semibold hover:underline">
              회원가입
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
            카카오로 로그인
          </Button>
          <Button
            type="button"
            variant="outline"
            class="w-full h-12 border-[#dee2e6] bg-white text-[#1e3a5f] font-medium rounded-xl hover:bg-[#f8f9fa] transition-colors"
          >
            네이버로 로그인
          </Button>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>

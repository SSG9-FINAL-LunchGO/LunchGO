<script setup>
import { ref } from 'vue'; // 상태 관리를 위해 ref 임포트
import { useRouter } from 'vue-router'; // 페이지 이동을 위해 router 임포트
import { User, Bell, LogOut } from 'lucide-vue-next';
import { RouterLink } from 'vue-router';

// --- Pinia 연동 부분 (예시) ---
// const authStore = useAuthStore();
// const userName = computed(() => authStore.user?.name || 'OOO');
const userName = ref('김사장'); // 임시 데이터

const router = useRouter();
const isMenuOpen = ref(false); // 메뉴 열림/닫힘 상태

// --- 메소드 ---

// 1. 메뉴 토글 기능
const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value;
};

// 2. 로그아웃 기능
const handleLogout = async () => {
  const confirmLogout = confirm('정말 로그아웃 하시겠습니까?');

  if (confirmLogout) {
    try {
      // 여기에 실제 로그아웃 로직 작성 (예: Pinia action 호출, 토큰 삭제 등)
      // await authStore.logout();
      console.log('로그아웃 로직 실행됨');

      // 로그아웃 후 첫 페이지로 이동
      router.push('/');
    } catch (error) {
      console.error('로그아웃 실패:', error);
    }
  }
};
</script>

<template>
  <header
    class="bg-white border-b border-[#e9ecef] px-8 py-5 flex items-center justify-between"
  >
    <RouterLink to="/business/dashboard">
      <h1
        class="text-2xl font-bold text-[#1E3A5F] hover:text-[#FF6B4A] transition-colors cursor-pointer"
      >
        LunchGo
      </h1>
    </RouterLink>

    <div class="flex items-center gap-4">
      <button class="text-[#1e3a5f] hover:text-[#FF6B4A] transition-colors">
        <Bell class="w-6 h-6" />
      </button>

      <div class="flex items-center gap-4">
        <span class="text-sm text-gray-600">
          <span class="text-[#1e3a5f] font-bold">{{ userName }}</span
          >님 안녕하세요!
        </span>

        <button
          @click="handleLogout"
          class="flex items-center gap-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-red-50 hover:text-red-500 hover:border-red-200 transition-colors"
        >
          <span class="text-sm">로그아웃</span>
          <LogOut class="w-4 h-4" />
        </button>
      </div>
    </div>
  </header>
</template>

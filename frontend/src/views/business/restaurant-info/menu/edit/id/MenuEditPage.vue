<script setup>
import { ref, reactive } from 'vue';
import { User, Bell, Upload, X } from 'lucide-vue-next'; // Import Lucide icons for Vue
import { RouterLink, useRouter, useRoute } from 'vue-router'; // Import Vue RouterLink, useRouter, and useRoute

const router = useRouter(); // Vue Router's useRouter
const route = useRoute(); // Vue Router's useRoute to access route params

const menuId = route.params.id; // Access the dynamic ID from the route params

const menuData = reactive({
  name: '김치찌개 ' + menuId, // Example: pre-fill with ID for uniqueness
  type: '콤보세트',
  category: '한식',
  price: '15000',
});

const selectedAllergens = ref(['대두', '계란']);

const toggleAllergen = (allergen) => {
  const index = selectedAllergens.value.indexOf(allergen);
  if (index > -1) {
    selectedAllergens.value.splice(index, 1);
  } else {
    selectedAllergens.value.push(allergen);
  }
};

const allergens = ref([
  '견과류',
  '우유',
  '계란',
  '밀',
  '대두',
  '갑각류',
  '조개류',
  '생선',
  '메밀',
]);
const specialAttributes = ref(['알레르기 - 알레르기 유발 or 호불호 강한']);

const saveMenu = () => {
  alert('메뉴가 수정되었습니다.');
  router.back();
};
</script>

<template>
  <div class="flex h-screen bg-[#f8f9fa]">
    <!-- Left Sidebar -->
    <aside class="w-64 bg-white border-r border-[#e9ecef] flex flex-col">
      <div class="p-6 border-b border-[#e9ecef]">
        <RouterLink to="/business/dashboard">
          <img src="/images/lunch-go-whitebg.png" alt="LunchGo" width="150" height="60" class="w-auto h-12" />
        </RouterLink>
      </div>

      <nav class="flex-1 p-4">
        <ul class="space-y-2">
          <li>
            <RouterLink
              to="/business/dashboard"
              class="block px-4 py-3 text-[#6c757d] hover:bg-[#f8f9fa] rounded-lg transition-colors"
            >
              오늘의 예약 현황
            </RouterLink>
          </li>
          <li>
            <RouterLink
              to="/business/reservations"
              class="block px-4 py-3 text-[#6c757d] hover:bg-[#f8f9fa] rounded-lg transition-colors"
            >
              전체 예약 관리
            </RouterLink>
          </li>
          <li>
            <RouterLink
              to="/business/restaurant-info"
              class="block px-4 py-3 text-[#1e3a5f] bg-[#fff5f2] rounded-lg font-semibold"
            >
              식당 정보
            </RouterLink>
          </li>
          <li>
            <RouterLink
              to="/business/staff"
              class="block px-4 py-3 text-[#6c757d] hover:bg-[#f8f9fa] rounded-lg transition-colors"
            >
              입점원 현황
            </RouterLink>
          </li>
          <li>
            <RouterLink
              to="/business/analytics"
              class="block px-4 py-3 text-[#6c757d] hover:bg-[#f8f9fa] rounded-lg transition-colors"
            >
              대시보드
            </RouterLink>
          </li>
        </ul>
      </nav>
    </aside>

    <!-- Main Content -->
    <div class="flex-1 flex flex-col overflow-hidden">
      <!-- Top Header -->
      <header class="bg-white border-b border-[#e9ecef] px-8 py-4 flex items-center justify-between">
        <div class="flex items-center gap-4">
          <button class="text-[#1e3a5f] hover:text-[#FF6B4A] transition-colors">
            <Bell class="w-6 h-6" />
          </button>
        </div>
        <div class="flex items-center gap-4">
          <div class="flex items-center gap-3 px-4 py-2 bg-[#f8f9fa] rounded-lg">
            <User class="w-5 h-5 text-[#6c757d]" />
            <span class="text-sm font-medium text-[#1e3a5f]">OOO님 안녕하세요!</span>
          </div>
          <button class="px-4 py-2 border border-[#dee2e6] rounded-lg text-[#1e3a5f] hover:bg-[#f8f9fa] transition-colors">
            로그아웃
          </button>
        </div>
      </header>

      <!-- Scrollable Content Area -->
      <main class="flex-1 overflow-y-auto p-8">
        <div class="max-w-2xl mx-auto">
          <!-- Page Title with Close Button -->
          <div class="flex items-center justify-between mb-8">
            <h2 class="text-3xl font-bold text-[#1e3a5f]">식당 메뉴 정보 수정</h2>
            <button @click="router.back()" class="p-2 hover:bg-[#f8f9fa] rounded-lg transition-colors">
              <X class="w-6 h-6 text-[#6c757d]" />
            </button>
          </div>

          <!-- Menu Form Card -->
          <div class="bg-white rounded-xl border border-[#e9ecef] p-8">
            <div class="space-y-6">
              <!-- Menu Image Upload -->
              <div>
                <label class="block text-sm font-semibold text-[#1e3a5f] mb-2">메뉴사진</label>
                <div class="border-2 border-dashed border-[#dee2e6] rounded-xl p-12 text-center bg-[#f8f9fa] hover:bg-[#e9ecef] transition-colors cursor-pointer">
                  <Upload class="w-12 h-12 text-[#6c757d] mx-auto mb-3" />
                  <p class="text-sm text-[#6c757d]">이미지</p>
                </div>
                <button class="mt-3 px-6 py-2 border border-[#dee2e6] rounded-lg text-[#1e3a5f] hover:bg-[#f8f9fa] transition-colors">
                  파일 업로드
                </button>
              </div>

              <!-- Menu Name -->
              <div>
                <label class="block text-sm font-semibold text-[#1e3a5f] mb-2">메뉴이름</label>
                <input
                  type="text"
                  placeholder="메뉴이름 입력창(텍스트)"
                  v-model="menuData.name"
                  class="w-full px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:ring-2 focus:ring-[#FF6B4A]"
                />
              </div>

              <!-- Menu Type -->
              <div>
                <label class="block text-sm font-semibold text-[#1e3a5f] mb-2">메뉴타입</label>
                <input
                  type="text"
                  placeholder="콤보세트(메뉴들, 서브메뉴들, 기타)"
                  v-model="menuData.type"
                  class="w-full px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:ring-2 focus:ring-[#FF6B4A]"
                />
              </div>

              <!-- Menu Category -->
              <div>
                <label class="block text-sm font-semibold text-[#1e3a5f] mb-2">가격</label>
                <input
                  type="text"
                  placeholder="가격 입력창"
                  v-model="menuData.price"
                  class="w-full px-4 py-3 border border-[#dee2e6] rounded-lg focus:outline-none focus:ring-2 focus:ring-[#FF6B4A]"
                />
              </div>

              <!-- Allergen Information -->
              <div>
                <label class="block text-sm font-semibold text-[#1e3a5f] mb-3">주재료</label>
                <div class="flex flex-wrap gap-3 mb-6">
                  <button
                    v-for="allergen in allergens"
                    :key="allergen"
                    @click="toggleAllergen(allergen)"
                    :class="`px-6 py-3 rounded-lg border transition-colors ${
                      selectedAllergens.includes(allergen)
                        ? 'gradient-primary text-white border-transparent'
                        : 'border-[#dee2e6] text-[#1e3a5f] hover:bg-[#f8f9fa]'
                    }`"
                  >
                    {{ allergen }}
                  </button>
                  <button class="px-6 py-3 border border-[#dee2e6] rounded-lg text-[#6c757d] hover:bg-[#f8f9fa] transition-colors">
                    ...
                  </button>
                </div>

                <label class="block text-sm font-semibold text-[#1e3a5f] mb-3">
                  특이사항(기타재료 - 알레르기 유발 or 호불호 강함)
                </label>
                <div class="flex flex-wrap gap-3">
                  <button
                    v-for="attr in specialAttributes"
                    :key="attr"
                    @click="toggleAllergen(attr)"
                    :class="`px-6 py-3 rounded-lg border transition-colors ${
                      selectedAllergens.includes(attr)
                        ? 'gradient-primary text-white border-transparent'
                        : 'border-[#dee2e6] text-[#1e3a5f] hover:bg-[#f8f9fa]'
                    }`"
                  >
                    {{ attr }}
                  </button>
                  <button class="px-6 py-3 border border-[#dee2e6] rounded-lg text-[#6c757d] hover:bg-[#f8f9fa] transition-colors">
                    ...
                  </button>
                </div>
              </div>
            </div>

            <!-- Action Buttons -->
            <div class="flex justify-end gap-3 mt-8">
              <button
                @click="router.back()"
                class="px-8 py-3 border border-[#dee2e6] rounded-lg text-[#6c757d] hover:bg-[#f8f9fa] transition-colors"
              >
                취소
              </button>
              <button
                @click="saveMenu"
                class="px-8 py-3 gradient-primary text-white rounded-lg font-semibold hover:opacity-90 transition-opacity"
              >
                저장
              </button>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>

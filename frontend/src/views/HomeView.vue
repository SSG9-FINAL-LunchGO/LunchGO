<script setup>
import { ref, computed } from 'vue';
import {
  MapPin,
  Calendar,
  Navigation,
  User,
  Star,
  X,
  Search,
  Minus,
  Plus,
  SlidersHorizontal,
} from 'lucide-vue-next'; // Import Lucide icons for Vue
import Button from '@/components/ui/Button.vue'; // Import our custom Button component
import Card from '@/components/ui/Card.vue';
import { RouterLink } from 'vue-router'; // Import Vue RouterLink

// State management (React's useState -> Vue's ref)
const isFilterOpen = ref(false);
const selectedPriceRanges = ref([]);
const selectedSort = ref('추천순');

const isSearchOpen = ref(false);
const searchDate = ref('');
const searchTime = ref('');
const searchCategories = ref([]);
const searchPartySize = ref(4);
const searchTags = ref([]);
const avoidIngredients = ref([]);
const searchDistance = ref('');
const budget = ref(500000);

// Static data (constants)
const categories = ['한식', '중식', '일식', '양식'];
const timeSlots = ['11:00', '12:00', '13:00', '14:00'];
const priceRanges = [
  '전체',
  '1만원 이하',
  '1만원~1.5만원',
  '1.5만원~2만원',
  '2만원~3만원',
  '3만원 이상',
];
const distances = ['1km 이내', '2km 이내', '3km 이내'];
const sortOptions = ['추천순', '거리순', '평점순', '가격순'];
const restaurantTags = [
  '조용한',
  '깔끔한',
  '노키즈존',
  '셀프바',
  '주차장 제공',
  '이국적/이색적',
  '칸막이',
  '룸',
  '단체',
];
const ingredients = [
  '견과류',
  '우유',
  '계란',
  '밀',
  '대두',
  '갑각류',
  '조개류',
  '생선',
  '메밀',
  '고수',
  '오이',
  '파프리카',
  '미나리',
  '당근',
];

// Event handlers
const togglePriceRange = (range) => {
  const index = selectedPriceRanges.value.indexOf(range);
  if (index > -1) {
    selectedPriceRanges.value.splice(index, 1);
  } else {
    selectedPriceRanges.value.push(range);
  }
};

const resetFilters = () => {
  selectedSort.value = '추천순';
  selectedPriceRanges.value = [];
};

const applyFilters = () => {
  isFilterOpen.value = false;
  // 필터 적용 로직 (Vue version)
};

const toggleSearchCategory = (category) => {
  const index = searchCategories.value.indexOf(category);
  if (index > -1) {
    searchCategories.value.splice(index, 1);
  } else {
    searchCategories.value.push(category);
  }
};

const toggleSearchTag = (tag) => {
  const index = searchTags.value.indexOf(tag);
  if (index > -1) {
    searchTags.value.splice(index, 1);
  } else {
    searchTags.value.push(tag);
  }
};

const toggleAvoidIngredient = (ingredient) => {
  const index = avoidIngredients.value.indexOf(ingredient);
  if (index > -1) {
    avoidIngredients.value.splice(index, 1);
  } else {
    avoidIngredients.value.push(ingredient);
  }
};

const resetSearch = () => {
  searchDate.value = '';
  searchTime.value = '';
  searchCategories.value = [];
  searchPartySize.value = 4;
  searchTags.value = [];
  avoidIngredients.value = [];
  searchDistance.value = '';
  budget.value = 500000;
};

const applySearch = () => {
  isSearchOpen.value = false;
  // 검색 적용 로직 (Vue version)
};

const restaurants = [
  {
    id: 1,
    name: '식당명',
    category: '한식',
    rating: 4.8,
    reviews: 245,
    topTags: [
      { name: '단체석이 넓어요', count: 89 },
      { name: '대화하기 좋아요', count: 67 },
    ],
    price: '18,000원(1인당) · 4인 ~ 12인',
    badgeColor: 'bg-emerald-500',
    image: '/modern-korean-restaurant-interior.jpg',
  },
  {
    id: 2,
    name: '식당명',
    category: '중식',
    rating: 4.8,
    reviews: 245,
    topTags: [
      { name: '음식이 빨리 나와요', count: 102 },
      { name: '양이 푸짐해요', count: 78 },
    ],
    price: '18,000원(1인당) · 4인 ~ 12인',
    badgeColor: 'bg-emerald-600',
    image: '/chinese-restaurant-food.jpg',
  },
  {
    id: 3,
    name: '식당명',
    category: '양식',
    rating: 4.8,
    reviews: 245,
    topTags: [
      { name: '룸이 있어 프라이빗해요', count: 95 },
      { name: '법카 쓰기 좋은 가격대에요', count: 71 },
    ],
    price: '18,000원(1인당) · 4인 ~ 12인',
    badgeColor: 'bg-amber-600',
    image: '/italian-restaurant-dining.jpg',
  },
  {
    id: 4,
    name: '식당명',
    category: '일식',
    rating: 4.7,
    reviews: 189,
    topTags: [
      { name: '호불호 없는 맛이에요', count: 82 },
      { name: '주차가 편해요', count: 56 },
    ],
    price: '22,000원(1인당) · 4인 ~ 12인',
    badgeColor: 'bg-rose-500',
    image: '/japanese-restaurant.png',
  },
  {
    id: 5,
    name: '식당명',
    category: '한식',
    rating: 4.9,
    reviews: 312,
    topTags: [
      { name: '특별한 메뉴가 있어요', count: 108 },
      { name: '사장님이 친절해요', count: 94 },
    ],
    price: '25,000원(1인당) · 4인 ~ 12인',
    badgeColor: 'bg-emerald-500',
    image: '/korean-fine-dining.jpg',
  },
];
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa]">
    <header class="sticky top-0 z-50 bg-white border-b border-[#e9ecef]">
      <div class="max-w-[500px] mx-auto px-4 h-14 flex items-center justify-between">
        <div class="flex items-center gap-3">
          <!-- Next.js Image component replaced with standard <img> -->
          <img src="/images/lunch-go-whitebg.png" alt="런치고" width="40" height="40" class="w-10 h-10" />
          <RouterLink
            to="/intro"
            class="font-semibold text-[#1e3a5f] text-base hover:text-[#ff6b4a] transition-colors"
          >
            서비스 소개
          </RouterLink>
        </div>
        <div class="flex items-center gap-4">
          <RouterLink to="/login" class="text-sm text-[#495057] font-medium hover:text-[#ff6b4a] transition-colors">
            로그인
          </RouterLink>
          <RouterLink to="/signup" class="text-sm text-[#495057] font-medium hover:text-[#ff6b4a] transition-colors">
            회원가입
          </RouterLink>
        </div>
      </div>
    </header>

    <main class="max-w-[500px] mx-auto pb-20">
      <div class="bg-white px-4 py-4 border-b border-[#e9ecef]">
        <div class="flex items-center gap-2">
          <MapPin class="w-5 h-5 text-[#ff6b4a]" />
          <div>
            <h2 class="text-base font-semibold text-[#1e3a5f]">강남구 삼성동</h2>
            <p class="text-xs text-[#6c757d]">현재 위치 기준</p>
          </div>
        </div>
      </div>

      <div class="relative bg-gradient-to-br from-blue-200 to-blue-300 h-64">
        <div class="absolute inset-0">
          <div class="relative w-full h-full">
            <div
              v-for="(pos, i) in [
                { left: '20%', top: '25%' },
                { left: '45%', top: '35%' },
                { left: '70%', top: '30%' },
                { left: '30%', top: '55%' },
                { left: '60%', top: '50%' },
                { left: '75%', top: '65%' },
              ]"
              :key="i"
              class="absolute"
              :style="{ left: pos.left, top: pos.top }"
            >
              <MapPin class="w-7 h-7 fill-blue-600 text-white drop-shadow-lg" />
            </div>

            <div class="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2">
              <div class="relative">
                <div class="absolute inset-0 bg-blue-500 rounded-full w-12 h-12 animate-ping opacity-30" />
                <div class="relative bg-white rounded-full p-2.5 shadow-lg">
                  <Navigation class="w-5 h-5 text-blue-600" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="px-4 py-5">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-[#1e3a5f]">오늘의 추천 회식 맛집</h3>
          <Button
            @click="isSearchOpen = true"
            variant="outline"
            size="sm"
            class="h-8 px-3 text-xs border-[#dee2e6] text-[#495057] bg-white hover:bg-[#f8f9fa] hover:text-[#1e3a5f] rounded-lg flex items-center gap-1"
          >
            <Search class="w-3.5 h-3.5" />
            검색
          </Button>
        </div>

        <div class="flex items-center gap-2 mb-4">
          <button
            @click="isFilterOpen = true"
            class="flex items-center gap-1.5 text-sm text-[#6c757d] hover:text-[#ff6b4a] transition-colors"
          >
            <SlidersHorizontal class="w-4 h-4" />
            <span>필터</span>
          </button>
        </div>

        <div class="space-y-3">
          <RouterLink v-for="restaurant in restaurants" :key="restaurant.id" :to="`/restaurant/${restaurant.id}`">
            <Card class="overflow-hidden border-[#e9ecef] rounded-xl bg-white shadow-card hover:shadow-lg transition-shadow cursor-pointer">
              <div class="flex gap-3 p-2">
                <div class="relative w-24 h-24 flex-shrink-0 rounded-lg overflow-hidden">
                  <img
                    :src="restaurant.image || '/placeholder.svg'"
                    :alt="restaurant.name"
                    class="w-full h-full object-cover"
                  />
                </div>

                <div class="flex-1 min-w-0">
                  <div class="flex items-center gap-2 mb-1">
                    <h4 class="font-semibold text-[#1e3a5f] text-sm">{{ restaurant.name }}</h4>
                    <span :class="`${restaurant.badgeColor} text-white text-xs font-medium px-2 py-0.5 rounded`">
                      {{ restaurant.category }}
                    </span>
                  </div>
                  <div class="flex items-center gap-1 mb-1.5">
                    <Star class="w-3.5 h-3.5 fill-[#ffc107] text-[#ffc107]" />
                    <span class="text-sm font-medium text-[#1e3a5f]">{{ restaurant.rating }}</span>
                    <span class="text-xs text-[#6c757d]">(리뷰수 : {{ restaurant.reviews }})</span>
                  </div>
                  <div class="flex flex-wrap gap-1 mb-2">
                    <span
                      v-for="(tag, index) in restaurant.topTags"
                      :key="index"
                      class="inline-flex items-center gap-1 text-xs px-2 py-0.5 rounded-full bg-gradient-to-r from-[#ff6b4a] to-[#ffc4b8] text-white"
                    >
                      {{ tag.name }} {{ tag.count }}
                    </span>
                  </div>
                  <p class="text-sm font-semibold text-[#1e3a5f]">{{ restaurant.price }}</p>
                </div>
              </div>
            </Card>
          </RouterLink>
        </div>
      </div>

      <footer class="bg-white border-t border-[#e9ecef] px-4 py-6 mt-8">
        <div class="text-xs text-[#6c757d] space-y-1 leading-relaxed">
          <p class="font-semibold text-[#1e3a5f]">(주) 런치고</p>
          <p>주소 : 경기도 성남시 분당구 대왕판교로 ~~</p>
          <p>
            서비스 이용 약관 | 개인정보 처리방침 | 위치정보 이용약관 | 환불 정책 |
            <RouterLink to="/partner" class="text-[#FF6B4A] hover:underline">
              입점 문의
            </RouterLink>
          </p>
        </div>
      </footer>
    </main>

    <!-- Bottom Navigation -->
    <nav class="fixed bottom-0 left-0 right-0 bg-white border-t border-[#e9ecef] z-50 shadow-lg">
      <div class="max-w-[500px] mx-auto flex items-center justify-around h-16 px-4">
        <RouterLink
          to="/my-reservations"
          class="flex items-center justify-center text-[#ff6b4a] hover:text-[#FF8A6D] transition-colors min-w-[60px]"
        >
          <Calendar class="w-6 h-6" />
        </RouterLink>

        <button class="flex items-center justify-center -mt-4">
          <div class="w-10 h-10 rounded-full gradient-primary flex items-center justify-center shadow-button-hover">
            <Navigation class="w-5 h-5 text-white" />
          </div>
        </button>

        <RouterLink
          to="/mypage"
          class="flex items-center justify-center text-[#ff6b4a] hover:text-[#FF8A6D] transition-colors min-w-[60px]"
        >
          <User class="w-6 h-6" />
        </RouterLink>
      </div>
    </nav>

    <!-- Filter Modal -->
    <div v-if="isFilterOpen" class="fixed inset-0 z-[100] bg-black/50 flex items-end">
      <div class="w-full max-w-[500px] mx-auto bg-white rounded-t-2xl max-h-[85vh] overflow-y-auto animate-in slide-in-from-bottom duration-300">
        <div class="sticky top-0 bg-white border-b border-[#e9ecef] px-4 py-4 flex items-center justify-between">
          <h3 class="text-lg font-semibold text-[#1e3a5f]">필터 및 정렬</h3>
          <button @click="isFilterOpen = false" class="text-[#6c757d] hover:text-[#1e3a5f]">
            <X class="w-6 h-6" />
          </button>
        </div>

        <div class="p-4 space-y-6">
          <!-- Sort Options -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">정렬</h4>
            <div class="flex flex-wrap gap-2">
              <button
                v-for="option in sortOptions"
                :key="option"
                @click="selectedSort = option"
                :class="`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                  selectedSort === option
                    ? 'gradient-primary text-white'
                    : 'bg-[#f8f9fa] text-[#495057] hover:bg-[#e9ecef]'
                }`"
              >
                {{ option }}
              </button>
            </div>
          </div>

          <!-- Price Range Filter -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">가격대</h4>
            <div class="flex flex-wrap gap-2">
              <button
                v-for="range in priceRanges"
                :key="range"
                @click="togglePriceRange(range)"
                :class="`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                  selectedPriceRanges.includes(range)
                    ? 'gradient-primary text-white'
                    : 'bg-[#f8f9fa] text-[#495057] hover:bg-[#e9ecef]'
                }`"
              >
                {{ range }}
              </button>
            </div>
          </div>
        </div>

        <div class="sticky bottom-0 bg-white border-t border-[#e9ecef] p-4 flex gap-3">
          <Button
            @click="resetFilters"
            variant="outline"
            class="flex-1 h-12 text-[#495057] border-[#dee2e6] hover:bg-[#f8f9fa] rounded-xl bg-transparent"
          >
            초기화
          </Button>
          <Button @click="applyFilters" class="flex-1 h-12 gradient-primary text-white rounded-xl">
            적용하기
          </Button>
        </div>
      </div>
    </div>

    <!-- Search Modal -->
    <div v-if="isSearchOpen" class="fixed inset-0 z-[100] bg-black/50 flex items-end">
      <div class="w-full max-w-[500px] mx-auto bg-white rounded-t-2xl max-h-[85vh] overflow-y-auto animate-in slide-in-from-bottom duration-300">
        <div class="sticky top-0 bg-white border-b border-[#e9ecef] px-4 py-4 flex items-center justify-between">
          <h3 class="text-lg font-semibold text-[#1e3a5f]">검색 필터</h3>
          <button @click="isSearchOpen = false" class="text-[#6c757d] hover:text-[#1e3a5f]">
            <X class="w-6 h-6" />
          </button>
        </div>

        <div class="p-4 space-y-6">
          <!-- Reservation Date -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">예약 날짜</h4>
            <input
              type="date"
              v-model="searchDate"
              class="w-full px-4 py-3 border border-[#dee2e6] rounded-lg text-sm text-[#495057] focus:outline-none focus:ring-2 focus:ring-[#ff6b4a] focus:border-transparent"
            />
          </div>

          <!-- Time Slot Selection -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">시간대</h4>
            <div class="grid grid-cols-4 gap-2">
              <button
                v-for="time in timeSlots"
                :key="time"
                @click="searchTime = time"
                :class="`px-4 py-3 rounded-lg text-sm font-medium transition-colors ${
                  searchTime === time
                    ? 'gradient-primary text-white'
                    : 'bg-[#f8f9fa] text-[#495057] hover:bg-[#e9ecef]'
                }`"
              >
                {{ time }}
              </button>
            </div>
          </div>

          <!-- Category -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">음식 종류</h4>
            <div class="flex flex-wrap gap-2">
              <button
                v-for="category in categories"
                :key="category"
                @click="toggleSearchCategory(category)"
                :class="`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                  searchCategories.includes(category)
                    ? 'gradient-primary text-white'
                    : 'bg-[#f8f9fa] text-[#495057] hover:bg-[#e9ecef]'
                }`"
              >
                {{ category }}
              </button>
            </div>
          </div>

          <!-- Party Size -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">인원 수</h4>
            <div class="flex items-center gap-4">
              <button
                @click="searchPartySize = Math.max(4, searchPartySize - 1)"
                class="w-10 h-10 rounded-lg border border-[#dee2e6] flex items-center justify-center hover:bg-[#f8f9fa] transition-colors disabled:opacity-50"
                :disabled="searchPartySize <= 4"
              >
                <Minus class="w-4 h-4 text-[#495057]" />
              </button>
              <div class="flex-1 text-center">
                <span class="text-2xl font-semibold text-[#1e3a5f]">{{ searchPartySize }}</span>
                <span class="text-sm text-[#6c757d] ml-1">명</span>
              </div>
              <button
                @click="searchPartySize = Math.min(12, searchPartySize + 1)"
                class="w-10 h-10 rounded-lg border border-[#dee2e6] flex items-center justify-center hover:bg-[#f8f9fa] transition-colors disabled:opacity-50"
                :disabled="searchPartySize >= 12"
              >
                <Plus class="w-4 h-4 text-[#495057]" />
              </button>
            </div>
          </div>

          <!-- Budget Slider -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">총 회식 가격</h4>
            <div class="space-y-3">
              <div class="text-center">
                <span class="text-2xl font-semibold text-[#1e3a5f]">
                  {{ budget >= 500000 ? '50만원 이상' : `${(budget / 10000).toFixed(0)}만원` }}
                </span>
              </div>
              <input
                type="range"
                min="0"
                max="500000"
                step="10000"
                v-model.number="budget"
                class="w-full h-2 bg-[#e9ecef] rounded-lg appearance-none cursor-pointer [&::-webkit-slider-thumb]:appearance-none [&::-webkit-slider-thumb]:w-5 [&::-webkit-slider-thumb]:h-5 [&::-webkit-slider-thumb]:rounded-full [&::-webkit-slider-thumb]:bg-[#ff6b4a] [&::-webkit-slider-thumb]:cursor-pointer"
              />
              <div class="flex justify-between text-xs text-[#6c757d]">
                <span>0원</span>
                <span>50만원 이상</span>
              </div>
            </div>
          </div>

          <!-- Distance Filter -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">거리</h4>
            <div class="flex flex-wrap gap-2">
              <button
                v-for="distance in distances"
                :key="distance"
                @click="searchDistance = distance"
                :class="`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                  searchDistance === distance
                    ? 'gradient-primary text-white'
                    : 'bg-[#f8f9fa] text-[#495057] hover:bg-[#e9ecef]'
                }`"
              >
                {{ distance }}
              </button>
            </div>
          </div>

          <!-- Restaurant Tags -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">식당 태그</h4>
            <div class="flex flex-wrap gap-2">
              <button
                v-for="tag in restaurantTags"
                :key="tag"
                @click="toggleSearchTag(tag)"
                :class="`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                  searchTags.includes(tag)
                    ? 'gradient-primary text-white'
                    : 'bg-[#f8f9fa] text-[#495057] hover:bg-[#e9ecef]'
                }`"
              >
                {{ tag }}
              </button>
            </div>
          </div>

          <!-- Avoid Ingredients -->
          <div>
            <h4 class="text-sm font-semibold text-[#1e3a5f] mb-3">기피 재료</h4>
            <div class="flex flex-wrap gap-2">
              <button
                v-for="ingredient in ingredients"
                :key="ingredient"
                @click="toggleAvoidIngredient(ingredient)"
                :class="`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                  avoidIngredients.includes(ingredient)
                    ? 'bg-red-500 text-white'
                    : 'bg-[#f8f9fa] text-[#495057] hover:bg-[#e9ecef]'
                }`"
              >
                {{ ingredient }}
              </button>
            </div>
          </div>
        </div>

        <div class="sticky bottom-0 bg-white border-t border-[#e9ecef] p-4 flex gap-3">
          <Button
            @click="resetSearch"
            variant="outline"
            class="flex-1 h-12 text-[#495057] border-[#dee2e6] hover:bg-[#f8f9fa] rounded-xl bg-transparent"
          >
            초기화
          </Button>
          <Button @click="applySearch" class="flex-1 h-12 gradient-primary text-white rounded-xl">
            검색하기
          </Button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Scoped styles can be added here if needed, but Tailwind handles most of it. */
</style>
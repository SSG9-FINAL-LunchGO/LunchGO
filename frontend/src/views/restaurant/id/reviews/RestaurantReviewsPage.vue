<script setup>
import { ref, computed } from 'vue';
import { RouterLink, useRoute } from 'vue-router';
import { ArrowLeft, Star, ChevronDown } from 'lucide-vue-next';
import Card from '@/components/ui/Card.vue';

const route = useRoute();
const restaurantId = route.params.id || '1'; // Default ID

const sortOrder = ref('추천순');
const isDropdownOpen = ref(false);

const reviews = ref([
  {
    id: 1,
    author: '김**',
    company: '한화시스템', // Added company name
    rating: 5,
    date: '2024.01.15',
    content: '회식하기 정말 좋았어요. 음식도 맛있고 분위기도 최고였습니다!',
    tags: ['룸이 있어 프라이빗해요', '대화하기 좋아요', '양이 푸짐해요'],
    images: [
      '/korean-appetizer-main-dessert.jpg',
      '/premium-course-meal-with-wine.jpg',
    ],
  },
  {
    id: 2,
    author: '이**',
    company: '네이버',
    rating: 5,
    date: '2024.01.10',
    content: '직원분들이 친절하시고 코스 구성이 알차서 만족스러웠습니다!',
    tags: ['사장님이 친절해요', '예약 시간 맞춰 세팅돼요'],
    images: [],
  },
  {
    id: 3,
    author: '박**',
    company: '카카오',
    rating: 4,
    date: '2024.01.05',
    content: '가격 대비 훌륭한 퀄리티입니다. 다음에 또 방문할게요.',
    tags: ['법카 쓰기 좋은 가격대에요', '주차가 편해요'],
    images: ['/italian-pasta-dish.png'],
  },
  {
    id: 4,
    author: '최**',
    company: '토스',
    rating: 5,
    date: '2024.01.03',
    content: '팀 회식으로 다녀왔는데 모두 만족했어요. 특히 C코스 추천합니다!',
    tags: ['단체석이 넓어요', '음식이 빨리 나와요', '호불호 없는 맛이에요'],
    images: ['/elegant-dining-room-setup.jpg'],
  },
  {
    id: 5,
    author: '정**',
    company: null,
    rating: 4,
    date: '2023.12.28',
    content: '음식이 정갈하고 맛있습니다. 예약 필수인 이유를 알겠어요.',
    tags: ['예약 시간 맞춰 세팅돼요', '특별한 메뉴가 있어요'],
    images: [],
  },
  {
    id: 6,
    author: '강**',
    company: '쿠팡',
    rating: 5,
    date: '2023.12.20',
    content: '회사 송년회로 다녀왔는데 분위기도 좋고 서비스도 훌륭했습니다.',
    tags: ['대화하기 좋아요', '룸이 있어 프라이빗해요', '옷 보관하기 편해요'],
    images: [
      '/modern-korean-restaurant-interior.jpg',
      '/restaurant-private-room-atmosphere.jpg',
    ],
  },
  {
    id: 7,
    author: '조**',
    company: '배달의민족',
    rating: 5,
    date: '2023.12.15',
    content: '음식 하나하나 정성이 느껴져요. 재방문 의사 100%입니다!',
    tags: ['양이 푸짐해요', '특별한 메뉴가 있어요'],
    images: ['/korean-course-meal-plating.jpg'],
  },
  {
    id: 8,
    author: '윤**',
    company: null,
    rating: 4,
    date: '2023.12.10',
    content: '가격대가 있지만 그만큼 가치가 있는 곳입니다. 특별한 날 추천해요.',
    tags: ['대화하기 좋아요', '특별한 메뉴가 있어요'],
    images: [],
  },
  {
    id: 9,
    author: '[블라인드]',
    company: null,
    rating: 0,
    date: '2023.12.08',
    content: '관리자에 의해 블라인드 처리된 리뷰입니다.',
    tags: [],
    images: [],
    isBlinded: true,
    blindReason: '욕설/비속어 포함',
  },
  {
    id: 10,
    author: '장**',
    company: '라인',
    rating: 5,
    date: '2023.12.05',
    content: '파스타가 정말 맛있었고, 와인 추천도 완벽했습니다!',
    tags: ['호불호 없는 맛이에요', '특별한 메뉴가 있어요'],
    images: ['/italian-pasta-dish.png'],
  },
  {
    id: 11,
    author: '임**',
    company: null,
    rating: 5,
    date: '2023.12.01',
    content: '직원분들 응대가 정말 좋고, 음식도 너무 맛있어요. 강력 추천!',
    tags: ['사장님이 친절해요', '회전율이 빨라요'],
    images: [],
  },
]);

const averageRating = computed(() => {
  const totalRating = reviews.value.reduce((sum, r) => sum + r.rating, 0);
  return (totalRating / reviews.value.length).toFixed(1);
});

const tagCounts = computed(() => {
  return reviews.value.reduce((acc, review) => {
    review.tags.forEach((tag) => {
      acc[tag] = (acc[tag] || 0) + 1;
    });
    return acc;
  }, {});
});

const topTags = computed(() => {
  return Object.entries(tagCounts.value)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 3);
});

const sortOptions = ref(['추천순', '최신순', '별점 높은순', '별점 낮은순']);

const setSortOrder = (option) => {
  sortOrder.value = option;
  isDropdownOpen.value = false;
};
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa]">
    <!-- Header -->
    <header class="sticky top-0 z-50 bg-white border-b border-[#e9ecef]">
      <div class="max-w-[500px] mx-auto px-4 h-14 flex items-center">
        <RouterLink :to="`/restaurant/${restaurantId}`" class="mr-3">
          <ArrowLeft class="w-6 h-6 text-[#1e3a5f]" />
        </RouterLink>
        <h1 class="font-semibold text-[#1e3a5f] text-base">리뷰</h1>
      </div>
    </header>

    <main class="max-w-[500px] mx-auto pb-8">
      <!-- Review Summary -->
      <div class="bg-white px-4 py-5 border-b border-[#e9ecef]">
        <div class="text-center">
          <div class="flex items-start gap-6 mb-4">
            <div class="flex flex-col items-center">
              <div class="text-4xl font-bold text-[#1e3a5f] mb-1">
                {{ averageRating }}
              </div>
              <div class="flex items-center gap-0.5 mb-1">
                <Star
                  v-for="(_, i) in Array.from({ length: 5 })"
                  :key="i"
                  :class="`w-4 h-4 ${
                    i < Math.round(Number(averageRating))
                      ? 'fill-[#ffc107] text-[#ffc107]'
                      : 'text-[#e9ecef]'
                  }`"
                />
              </div>
              <div class="text-xs text-[#6c757d]">
                {{ reviews.length }}개 리뷰
              </div>
            </div>

            <div class="flex-1 text-left">
              <p class="text-xs text-[#6c757d] leading-relaxed mb-3">
                고객님들이 남겨주신 소중한 리뷰입니다. 실제 방문 후 작성된
                리뷰만 표시됩니다.
              </p>
            </div>
          </div>

          <div class="space-y-2">
            <div
              v-for="([tag, count], idx) in topTags"
              :key="idx"
              class="flex items-center justify-between"
            >
              <span
                class="px-4 py-2 text-sm rounded-full bg-gradient-to-r from-[#ff6b4a] to-[#ffc4b8] text-white flex-1 text-center"
              >
                {{ tag }}
              </span>
              <span class="text-base font-semibold text-[#1e3a5f] ml-3">{{
                count
              }}</span>
            </div>
            <p
              v-if="topTags.length > 0"
              class="text-xs text-[#6c757d] text-right mt-2"
            >
              상위 {{ topTags.length }}개 태그만 표시
            </p>
          </div>
        </div>

        <div class="px-4 pt-4 pb-2">
          <div class="relative inline-block">
            <button
              @click="isDropdownOpen = !isDropdownOpen"
              class="flex items-center gap-2 px-4 py-2 bg-[#6c757d] text-white rounded-lg text-sm font-medium hover:bg-[#5a6268] transition-colors"
            >
              {{ sortOrder }}
              <ChevronDown class="w-4 h-4" />
            </button>
            <div
              v-if="isDropdownOpen"
              class="absolute top-full mt-1 left-0 bg-white border border-[#e9ecef] rounded-lg shadow-lg py-1 min-w-[140px] z-10"
            >
              <button
                v-for="option in sortOptions"
                :key="option"
                @click="setSortOrder(option)"
                :class="`block w-full text-left px-4 py-2 text-sm hover:bg-[#f8f9fa] transition-colors ${
                  sortOrder === option
                    ? 'text-[#ff6b4a] font-semibold'
                    : 'text-[#495057]'
                }`"
              >
                {{ option }}
              </button>
            </div>
          </div>
        </div>

        <!-- Reviews List -->
        <div class="px-4 py-3 space-y-3">
          <RouterLink
            v-for="review in reviews"
            :key="review.id"
            :to="`/restaurant/${restaurantId}/reviews/${review.id}`"
          >
            <Card
              :class="`p-4 border-[#e9ecef] rounded-xl bg-white shadow-card hover:shadow-md transition-shadow cursor-pointer ${
                review.isBlinded ? 'opacity-60' : ''
              }`"
            >
              <div class="flex items-center justify-between mb-2">
                <div class="flex items-center gap-2">
                  <span class="font-semibold text-[#1e3a5f] text-sm">
                    {{ review.author }}
                    <span
                      v-if="review.company"
                      class="text-[#6c757d] font-normal"
                    >
                      ({{ review.company }})</span
                    >
                  </span>
                  <div v-if="!review.isBlinded" class="flex items-center gap-1">
                    <Star
                      v-for="(_, i) in Array.from({ length: review.rating })"
                      :key="i"
                      class="w-3.5 h-3.5 fill-[#ffc107] text-[#ffc107]"
                    />
                  </div>
                </div>
                <span class="text-xs text-[#6c757d]">{{ review.date }}</span>
              </div>
              <p class="text-sm text-[#495057] leading-relaxed mb-2">
                {{ review.content }}
              </p>
              <div v-if="review.isBlinded" class="mt-2">
                <span
                  class="inline-block px-2.5 py-1 text-xs rounded-full bg-[#6c757d] text-white"
                >
                  사유: {{ review.blindReason }}
                </span>
              </div>
              <div
                v-else-if="review.tags && review.tags.length > 0"
                class="flex flex-wrap gap-1.5"
              >
                <span
                  v-for="(tag, idx) in review.tags"
                  :key="idx"
                  class="inline-block px-2.5 py-1 text-xs rounded-full bg-gradient-to-r from-[#ff6b4a] to-[#ffc4b8] text-white"
                >
                  {{ tag }}
                </span>
              </div>
            </Card>
          </RouterLink>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>

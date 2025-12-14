<script setup>
import { computed } from 'vue';
import { RouterLink, useRoute } from 'vue-router'; // Import useRoute
import { ArrowLeft, Star } from 'lucide-vue-next';
import Card from '@/components/ui/Card.vue';

const route = useRoute();
const restaurantId = route.params.id || '1'; // Default ID
const reviewId = route.params.reviewId;

// Mock data - in production, fetch based on reviewId
const reviews = [
  {
    id: '1',
    author: '김**',
    company: '네이버',
    rating: 5,
    date: '2024.01.15',
    content: '회식하기 정말 좋았어요. 음식도 맛있고 분위기도 최고였습니다!',
    tags: ['룸이 있어 프라이빗해요', '대화하기 좋아요', '양이 푸짐해요'],
    images: ['/korean-appetizer-main-dessert.jpg', '/premium-course-meal-with-wine.jpg'],
    detailedContent: `회사 팀 회식으로 방문했는데 정말 만족스러웠습니다.

먼저 룸으로 안내받아서 다른 손님들 눈치 보지 않고 편하게 대화하며 식사할 수 있었어요. 음식은 하나하나 정성스럽게 나왔고, 특히 메인 요리가 정말 맛있었습니다.

직원분들도 친절하시고 서비스가 세심했어요. 가격대는 있지만 그만큼 충분히 가치가 있는 곳입니다. 

다음 회식 때도 꼭 다시 방문하고 싶습니다!`,
  },
  {
    id: '2',
    author: '이**',
    company: null,
    rating: 5,
    date: '2024.01.10',
    content: '직원분들이 친절하시고 코스 구성이 알차서 만족스러웠습니다!',
    tags: ['사장님이 친절해요', '예약 시간 맞춰 세팅돼요'],
    images: [],
    detailedContent: `B코스를 주문했는데 구성이 정말 알차더라고요. 전채부터 메인, 디저트까지 하나하나 맛있었습니다.

특히 직원분들이 정말 친절하셔서 기분 좋게 식사할 수 있었어요. 음식 설명도 자세히 해주시고, 필요한 게 있으면 바로바로 챙겨주셨습니다.

회식 장소로 강력 추천합니다!`,
  },
  {
    id: '3',
    author: '박**',
    company: '카카오',
    rating: 4,
    date: '2024.01.05',
    content: '가격 대비 훌륭한 퀄리티입니다. 다음에 또 방문할게요.',
    tags: ['법카 쓰기 좋은 가격대에요', '주차가 편해요'],
    images: ['/italian-pasta-dish.png'],
    detailedContent: `주차장이 있어서 편하게 방문할 수 있었고, 음식 퀄리티도 가격 대비 정말 훌륭했습니다.

파스타를 주문했는데 면의 식감도 좋고 소스도 진하고 맛있었어요. 양도 적당해서 다른 메뉴도 함께 주문해서 나눠 먹기 좋았습니다.

다음에는 코스 메뉴도 도전해보고 싶네요!`,
  },
  {
    id: '9',
    author: '[블라인드]',
    company: null,
    rating: 0,
    date: '2023.12.08',
    content: '관리자에 의해 블라인드 처리된 리뷰입니다.',
    tags: [],
    images: [],
    isBlinded: true,
    blindReason: '욕설/비속어 포함',
    detailedContent: '이 리뷰는 약관 위반으로 블라인드 처리되었습니다. 런치고는 건전한 리뷰 문화를 위해 노력하고 있습니다.',
  },
];

const review = computed(() => {
  return reviews.find((r) => r.id === reviewId) || reviews[0];
});
</script>

<template>
  <div class="min-h-screen bg-[#f8f9fa]">
    <!-- Header -->
    <header class="sticky top-0 z-50 bg-white border-b border-[#e9ecef]">
      <div class="max-w-[500px] mx-auto px-4 h-14 flex items-center">
        <RouterLink :to="`/restaurant/${restaurantId}/reviews`" class="mr-3">
          <ArrowLeft class="w-6 h-6 text-[#1e3a5f]" />
        </RouterLink>
        <h1 class="font-semibold text-[#1e3a5f] text-base">리뷰 상세</h1>
      </div>
    </header>

    <main class="max-w-[500px] mx-auto pb-8">
      <Card
        :class="`m-4 p-5 border-[#e9ecef] rounded-xl bg-white shadow-card ${
          review.isBlinded ? 'opacity-60' : ''
        }`"
      >
        <!-- User Info -->
        <div class="flex items-center justify-between mb-4">
          <div>
            <div class="flex items-center gap-2 mb-1">
              <span class="font-bold text-[#1e3a5f] text-base">{{ review.author }}</span>
              <template v-if="review.company">
                <span class="text-[#adb5bd]">•</span>
                <span class="text-sm text-[#6c757d]">{{ review.company }}</span>
              </template>
            </div>
            <div v-if="!review.isBlinded" class="flex items-center gap-1">
              <Star
                v-for="(_, i) in Array.from({ length: review.rating })"
                :key="i"
                class="w-4 h-4 fill-[#ffc107] text-[#ffc107]"
              />
            </div>
          </div>
          <span class="text-sm text-[#6c757d]">{{ review.date }}</span>
        </div>

        <!-- Review Images -->
        <div v-if="review.images && review.images.length > 0" class="mb-4">
          <div class="grid grid-cols-2 gap-2">
            <div v-for="(img, idx) in review.images" :key="idx" class="relative aspect-square rounded-lg overflow-hidden">
              <img
                :src="img || '/placeholder.svg'"
                :alt="`리뷰 이미지 ${idx + 1}`"
                class="w-full h-full object-cover"
              />
            </div>
          </div>
        </div>

        <!-- Review Tags or Blind Reason -->
        <div v-if="review.isBlinded" class="mb-4">
          <span class="inline-block px-3 py-1.5 text-sm rounded-full bg-[#6c757d] text-white font-medium">
            사유: {{ review.blindReason }}
          </span>
        </div>
        <div v-else-if="review.tags && review.tags.length > 0" class="flex flex-wrap gap-2 mb-4">
          <span
            v-for="(tag, idx) in review.tags"
            :key="idx"
            class="inline-block px-3 py-1.5 text-sm rounded-full bg-gradient-to-r from-[#ff6b4a] to-[#ffc4b8] text-white font-medium"
          >
            {{ tag }}
          </span>
        </div>

        <!-- Review Content -->
        <div class="text-sm text-[#495057] leading-relaxed whitespace-pre-line">
          {{ review.detailedContent || review.content }}
        </div>
      </Card>
    </main>
  </div>
</template>

<style scoped>
/* No specific styles needed here as Tailwind handles most of it. */
</style>

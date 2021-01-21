from django.urls import path
from article.views import ArticleView, ArticleDetailView


urlpatterns = [
    path('articles/', ArticleView.as_view(), name='article-list'),
    path('articles/<int:article_id>/', ArticleDetailView.as_view(), name='article-detail'),
]
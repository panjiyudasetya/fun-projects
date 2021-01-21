from rest_framework import generics

from article.models import Article
from article.serializers import ArticleSerializer


class ArticleView(generics.ListAPIView):
    serializer_class = ArticleSerializer
    queryset = Article.objects.all().filter(author_id=1)


class ArticleDetailView(generics.UpdateAPIView):
    serializer_class = ArticleSerializer
    queryset = Article.objects.all()
    lookup_url_kwarg = 'article_id'
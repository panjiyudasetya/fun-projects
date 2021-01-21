from django.contrib import admin
from article.models import Article, Comment

class ArticleAdmin(admin.ModelAdmin):
    pass

class CommentAdmin(admin.ModelAdmin):
    pass

admin.site.register(Comment, CommentAdmin)
admin.site.register(Article, ArticleAdmin)
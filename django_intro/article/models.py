from django.db import models 
from django.contrib.auth import get_user_model


User = get_user_model()


class Article(models.Model):
	title = models.CharField(max_length=128)
	content = models.TextField()
	comments = models.ManyToManyField('Comment')
	created_at = models.DateTimeField(auto_now_add=True)
	updated_at = models.DateTimeField(auto_now=True)
	author = models.ForeignKey(User, on_delete=models.CASCADE)


class Comment(models.Model):
	value = models.CharField(max_length=255)
	created_at = models.DateTimeField(auto_now_add=True)
	updated_at = models.DateTimeField(auto_now=True)
	user = models.ForeignKey(User, on_delete=models.CASCADE)

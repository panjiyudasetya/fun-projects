# Generated by Django 3.1.5 on 2021-01-21 05:03

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('article', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='article',
            name='comments',
            field=models.ManyToManyField(to='article.Comment'),
        ),
    ]

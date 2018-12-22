from django.db import models

class Post(models.Model):
    title = models.CharField(max_length = 70)
    name = models.CharField(max_length = 25)
    link = models.CharField(max_length = 500)
    date_submitted = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.title
from django import forms
from liteboardbackend.models import Post


class SubmitForm(forms.ModelForm):
    title = forms.CharField(max_length=70)
    name = forms.CharField(max_length=25)
    link = forms.CharField(max_length=500)

    class Meta:
        fields = ("title", "name", "link")
        model = Post

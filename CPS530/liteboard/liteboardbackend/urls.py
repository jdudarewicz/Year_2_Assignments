from django.urls import path, re_path, include
from django.views.generic import ListView, DetailView
from . import views
from liteboardbackend.models import Post

urlpatterns = [
    re_path('^homepage/', ListView.as_view(queryset=Post.objects.all().order_by("-date_submitted"), template_name="home.html"), name = 'demohome'),
    re_path('^submit/$', views.form, name = 'submit'),
    re_path('^$', views.home, name = 'home'),
    re_path('^install_tutorial/$', views.install, name = 'install'),
    re_path('^demo_tutorial/$', views.demo, name = 'demo'),
    re_path('^conclusion/$', views.conclusion, name = 'conclusion'),
    re_path('^credits/$', views.credit, name = 'credits')
]
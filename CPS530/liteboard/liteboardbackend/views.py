from django.shortcuts import render, redirect
from django.template import RequestContext
from liteboardbackend.forms import SubmitForm
from liteboardbackend.models import Post

def homepage(request):
        return render(request, 'home.html')

def home(request):
        return render(request, 'framework_summary.html')

def install(request):
        return render(request, 'install_tutorial.html')

def demo(request):
        return render(request, 'demo_tutorial.html')

def conclusion(request):
        return render(request, 'conclusion.html')

def credit(request):
        return render(request, 'credits.html')

def form(request):
        if request.method == 'POST':
                form = SubmitForm(request.POST)
                if form.is_valid():
                        form.save()
                        return redirect('demohome')
        form = SubmitForm()
        return render(request, 'submit.html', {'form': form})
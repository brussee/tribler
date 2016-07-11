from pythonforandroid.toolchain import PythonRecipe


class FunctoolsLruCacheRecipe(PythonRecipe):

    version = '1.2.1'

    url = 'http://pypi.python.org/packages/87/77/5b2fd33e46c8ed4d67b45337d9f7bb27d1a1d577536470b39c267b5ce093/backports.functools_lru_cache-{version}.tar.gz'

    depends = ['hostpython2', 'setuptools', 'setuptools_scm']

    site_packages_name = 'backports.functools-lru-cache'

    call_hostpython_via_targetpython = False


recipe = FunctoolsLruCacheRecipe()

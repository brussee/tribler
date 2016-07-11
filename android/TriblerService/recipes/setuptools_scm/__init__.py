from pythonforandroid.toolchain import PythonRecipe


class SetuptoolsScmRecipe(PythonRecipe):

    version = '1.11.1'

    url = 'http://pypi.python.org/packages/84/aa/c693b5d41da513fed3f0ee27f1bf02a303caa75bbdfa5c8cc233a1d778c4/setuptools_scm-{version}.tar.gz'

    depends = ['hostpython2', 'setuptools']

    site_packages_name = 'setuptools-scm'

    call_hostpython_via_targetpython = False


recipe = SetuptoolsScmRecipe()

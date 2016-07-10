from pythonforandroid.toolchain import PythonRecipe


class CoverageRecipe(PythonRecipe):

    version = '2.85'

    url = 'http://nedbatchelder.com/code/modules/coverage-{version}.tar.gz'

    depends = ['hostpython2', 'setuptools']

    site_packages_name = 'coverage'

    call_hostpython_via_targetpython = False


recipe = CoverageRecipe()

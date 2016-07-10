from pythonforandroid.toolchain import PythonRecipe


class CoverageRecipe(PythonRecipe):

    version = '2.85'

    url = 'http://nedbatchelder.com/code/modules/coverage-{version}.tar.gz'

    depends = ['hostpython2']

    site_packages_name = 'coverage'


recipe = CoverageRecipe()

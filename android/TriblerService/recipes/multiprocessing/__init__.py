from pythonforandroid.toolchain import NDKRecipe, shprint, shutil, current_directory
from os.path import join, exists
import sh


class MultiprocessingRecipe(NDKRecipe):

    generated_libraries = ['multiprocessing']

    def should_build(self, arch):
        return not self.has_libs(arch, 'libmultiprocessing.so')

    def prebuild_arch(self, arch):
        super(MultiprocessingRecipe, self).prebuild_arch(arch)
        # Copy the Android make file
        sh.mkdir('-p', join(self.get_build_dir(arch.arch), 'jni'))
        shutil.copyfile(join(self.get_recipe_dir(), 'Android.mk'),
                        join(self.get_build_dir(arch.arch), 'jni/Android.mk'))
        # Copy the native source from python2
        r = self.get_recipe('python2', self.ctx)

        shutil.rmtree(join(self.get_build_dir(arch.arch), 'src'), ignore_errors=True)

        shutil.copytree(join(r.get_build_dir(arch.arch), 'Modules/_multiprocessing'),
                        join(self.get_build_dir(arch.arch), 'src'))

        shutil.rmtree(join(self.get_build_dir(arch.arch), 'Include'), ignore_errors=True)

        shutil.copytree(join(r.get_build_dir(arch.arch), 'Include'),
                        join(self.get_build_dir(arch.arch), 'Include'))

    def build_arch(self, arch, *extra_args):
        super(MultiprocessingRecipe, self).build_arch(arch)
        # Copy the shared library
        shutil.copyfile(join(self.get_build_dir(arch.arch), 'libs', arch.arch, 'libmultiprocessing.so'),
                        join(self.ctx.get_libs_dir(arch.arch), 'libmultiprocessing.so'))

    def get_recipe_env(self, arch):
        env = super(MultiprocessingRecipe, self).get_recipe_env(arch)
        env['NDK_PROJECT_PATH'] = self.get_build_dir(arch.arch)
        env['CFLAGS'] += ' -I' + join(self.get_build_dir(arch.arch) + '/Include')
        #env['PYTHON_ROOT'] = self.ctx.get_python_install_dir()
        #env['CFLAGS'] += ' -I' + env['PYTHON_ROOT'] + '/include/python2.7'
        #env['LDFLAGS'] += ' -L' + env['PYTHON_ROOT'] + '/lib' + \
        #                  ' -lpython2.7'
        return env


recipe = MultiprocessingRecipe()

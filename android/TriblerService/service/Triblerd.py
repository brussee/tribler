import os

from twisted.internet import reactor

from tribler_plugin import service_maker, Options


class Triblerd(object):

    def __init__(self):
        '''
        Setup environment
        '''
        private_root_dir = os.path.realpath(os.path.split(os.getenv('ANDROID_PRIVATE', '/tmp/cache'))[0])
        os.environ['PYTHON_EGG_CACHE'] = os.path.join(private_root_dir, 'cache', '.egg')

    def run(self):
        '''
        Pass through options
        '''
        options = Options()
        Options.parseOptions(options, os.getenv('PYTHON_SERVICE_ARGUMENT', ''))
        service_maker.makeService(options)
        reactor.run()

    def test(self):
        print "-------------------- IMPORT"
        from Tribler.Test.Community.Multichain.test_multichain_scale import TestMultiChainScale
        print "-------------------- CLASS"
        test = TestMultiChainScale()
        print "-------------------- SETUP"
        test.setUp()
        print "-------------------- RUN TEST"
        test.test_schedule_many_blocks(blocks_in_thousands=10)
        print "-------------------- TEARDOWN"
        test.tearDown()
        print "-------------------- DONE"


if __name__ == '__main__':
    Triblerd().test()
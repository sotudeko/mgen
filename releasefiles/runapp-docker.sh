#!/bin/sh

workdir=$(pwd)
metricsdir=${workdir}/metrics

docker run --rm -it -v ${workdir}:/config -v ${metricsdir}:/metrics ghcr.io/sotudeko/mgen:@APPVER@



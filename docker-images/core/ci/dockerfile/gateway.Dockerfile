FROM bkci/openrestry:0.0.1

LABEL maintainer="Tencent BlueKing Devops"

ENV INSTALL_PATH="/data/workspace/"

COPY ./ci-docker/gateway /data/workspace/gateway
COPY ./ci-docker/support-files/templates /data/workspace/templates
COPY ./ci-docker/scripts /data/workspace/scripts
COPY ./ci-docker/agent-package/script/docker_init.sh /data/dir/gateway/files/prod/
COPY ./ci-docker/agent-package/jar /data/dir/gateway/files/prod/jar

RUN rm -rf /usr/local/openresty/nginx/conf &&\
    ln -s  /data/workspace/gateway/core /usr/local/openresty/nginx/conf &&\
    chmod +x /data/workspace/scripts/render_tpl &&\
    mkdir -p /usr/local/openresty/nginx/run/ &&\
    mkdir -p ${BK_CI_LOGS_DIR}/nginx/ ${BK_CI_HOME} &&\
    chown nobody:nobody ${BK_CI_LOGS_DIR}/nginx/ &&\
    ln -s /data/workspace/frontend ${BK_CI_HOME}/frontend &&\
    cp -r /data/workspace/scripts/render_tpl /usr/local/openresty/nginx/ &&\
    touch /usr/local/openresty/nginx/bkenv.properties &&\
    ./render_tpl -m . /data/workspace/templates/gateway* &&\
    ./render_tpl -m . /data/workspace/templates/frontend*

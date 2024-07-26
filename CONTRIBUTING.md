# Contributing

## Code style
Java code is formatted with [google-java-format]. Extensions are available for Eclipse and Idea, and the formatting
is checked by CI.

## Useful Git configuration
The following Git config options may be useful to you:

### Exclude sweeping formatting commits from `git blame`
```shell
git config blame.ignoreRevsFile .git-blame-ignore-revs
```
This tells Git to exclude the commits mentioned in `.git-blame-ignore-revs` for the purposes of `git blame`. 
GitHub does this automatically for its blames.

### Stop `git fetch` complaining about tag clobbering
Only run this one once, and only if you actually want to fetch tags from the repo!
```shell
git config --add remote.origin.fetch +refs/tags/unstable:refs/tags/unstable
```
By default, once Git fetches a tag from a remote, that tag is considered immutable, and won't be updated even if
it changes on the remote (unless `--force` is passed to the relevant Git command). The above command tells Git to
ignore this rule for the tag `unstable` (since that tag is kept at the current head of `master` by CI), and instead
update it with every fetch. For more information on this, see [the relevant part of the Git docs][git-fetch-refspec]

[git-fetch-refspec]: https://git-scm.com/docs/git-fetch#CRTB
[google-java-format]: https://github.com/google/google-java-format